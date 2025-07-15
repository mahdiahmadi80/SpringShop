package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.AddressException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.exception.userException.VerifyException;
import org.example.springshop.model.Address;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
import org.example.springshop.repository.AddressRepository;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.repository.WalletRepository;
import org.example.springshop.service.securityservice.JWTService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.springshop.config.SecurityConfig.generateMD5Hash;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final WalletService walletService;
    private final JWTService jwtService;
    private final WalletRepository walletRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, WalletService walletService, JWTService jwtService, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.walletService = walletService;
        this.jwtService = jwtService;
        this.walletRepository = walletRepository;
    }

    public List<UserResponseModel> listUser() {
        List<UserResponseModel> userResponseModels = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            UserResponseModel userResponseModel = UserResponseModel.builder().user(user).build();
            userResponseModels.add(userResponseModel);
        });
        return userResponseModels;
    }

    @Transactional
    public UserResponseModel addUser(UserRequestModel userRequestModel) {
        userRequestModel.setPassword(generatePassword(userRequestModel.getPassword()));

        User user = User.userBuilder().userRequestModel(userRequestModel).build();
        Wallet wallet = Wallet.userWalletClass().user(user).build();

        walletRepository.save(wallet);
        userRepository.save(user);
        return UserResponseModel.builder().user(user).build();
    }

    public String generatePassword(String password) {
        return generateMD5Hash(password);
    }

    @Transactional
    public UserResponseModel signUpUser(Long id, UserRequestModel userRequestModel) {
//        User user = userRepository.findById(id).orElseThrow();
        User user = searchUser(id);
        user.setLastName(userRequestModel.getLastName());
        user.setEmail(userRequestModel.getEmail());
        user.setPhoneNumber(userRequestModel.getPhoneNumber());
        user.setNationalCode(userRequestModel.getNationalcode());

        Address address = addressRepository.findById(userRequestModel.getAddressId()).orElseThrow(() -> new AddressException("address not found"));

        address.setUserId(user);

        userRepository.save(user);
        return UserResponseModel.builder().user(user).build();
    }

    public UserResponseModel editUser(Long id, UserRequestModel userRequestModel) {
        User updateUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
        updateUser.setName(userRequestModel.getName());
        updateUser.setPassword(generatePassword(userRequestModel.getPassword()));
        updateUser.setRole(userRequestModel.getUserRole());
        User user = userRepository.save(updateUser);
        return UserResponseModel.builder().user(user).build();
    }


    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        Wallet wallet = walletRepository.findWalletByUserId(user.getId()).orElseThrow();
        walletRepository.delete(wallet);
        userRepository.delete(user);
        return "user is deleted";
    }

    public User createWallet(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        Wallet wallet = Wallet.userWalletClass().user(user).build();
//        Wallet wallet =  walletRepository.findWalletByUserId(user).orElseThrow();
        walletService.createWallet(wallet);
        return user;
    }

    public UserResponseModel uploadProfilePicture(Long id, String pictureUrl) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
        user.setProfilePicture(pictureUrl);
        userRepository.save(user);
        return UserResponseModel.builder().user(user).build();
    }

    public String verify(UserRequestModel userRequestModel) {
        String hashedPassword = generateMD5Hash(userRequestModel.getPassword());
        User userPass = userRepository.findByName(userRequestModel.getName()).orElseThrow(() -> new UserNotFoundException("user not found"));
        return checkPassword(hashedPassword, userPass, userRequestModel);
    }

    public String checkPassword(String hashedPassword, User user, UserRequestModel userRequestModel) {
        String token;
        if (hashedPassword.equals(user.getPassword())) {
            token = jwtService.generateToken(userRequestModel.getName());
        } else {
            throw new VerifyException("password not true");
        }
        return token;
    }

    public User searchUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserResponseModel searchUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseModel.builder().user(user).build();
    }

    public UserResponseModel searchByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseModel.builder().user(user).build();
    }

    public UserResponseModel searchByNationalCode(String nationalCode) {
        User user = userRepository.findByNationalCode(nationalCode).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseModel.builder().user(user).build();
    }

    public UserResponseModel searchByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));
        return UserResponseModel.builder().user(user).build();
    }

    public List<UserResponseModel> searchUserByRole(Long role) {
        List<UserResponseModel> userResponseModels = new ArrayList<>();
        userRepository.searchUserByRole(role).forEach(user -> {
            UserResponseModel userResponseModel = UserResponseModel.builder().user(user).build();
            userResponseModels.add(userResponseModel);
        });
        return userResponseModels;
    }


}
