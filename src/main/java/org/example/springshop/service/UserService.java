package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.exception.userException.VerifyException;
import org.example.springshop.model.Address;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
import org.example.springshop.repository.AddressRepository;
import org.example.springshop.repository.UserRepository;
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

    public UserService(UserRepository userRepository, AddressRepository addressRepository, WalletService walletService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.walletService = walletService;
        this.jwtService = jwtService;
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
        userRepository.save(user);
        return UserResponseModel.builder().user(user).build();
    }

    public String generatePassword(String password) {
        return generateMD5Hash(password);
    }

    @Transactional
    public UserResponseModel signUpUser(Long id, UserRequestModel userRequestModel) {
        User user = searchUser(id);
        if (userRequestModel.getLastName() != null && userRequestModel.getLastName().isPresent()) {
            user.setLastName(userRequestModel.getLastName().get());
        }
        if (userRequestModel.getEmail() != null && userRequestModel.getEmail().isPresent()) {
            user.setEmail(userRequestModel.getEmail().get());
        }
        if (userRequestModel.getPhoneNumber() != null && userRequestModel.getPhoneNumber().isPresent()) {
            user.setPhoneNumber(userRequestModel.getPhoneNumber().get());
        }
        if (userRequestModel.getNationalcode() != null && userRequestModel.getNationalcode().isPresent()) {
            user.setNationalCode(userRequestModel.getNationalcode().get());
        }
        if (userRequestModel.getAddressId() != null && userRequestModel.getAddressId().isPresent()) {
            Address address = addressRepository.findById(userRequestModel.getAddressId().get()).orElseThrow();
            user.setAddress(address);
        }
        if (user.getWallet() == null) {
            user.setWallet(createWallet(user));
        }
        userRepository.save(user);
        return UserResponseModel.builder().user(user).build();
    }

    public UserResponseModel editUser(Long id, UserRequestModel userRequestModel) {
        User updateUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));

//        String hashedPassword = generateMD5Hash(userRequestModel.getPassword());
        updateUser.setName(userRequestModel.getName());
        updateUser.setPassword(generatePassword(userRequestModel.getPassword()));
        updateUser.setRole(userRequestModel.getUserRole());
        User user = userRepository.save(updateUser);
        return UserResponseModel.builder().user(user).build();
    }

    public String deleteUser(Long id) {
         userRepository.deleteById(id);
        return "user is deleted";

    }

    public Wallet createWallet(User user) {
        Wallet wallet = Wallet.userWalletClass().user(user).build();
        walletService.createWallet(wallet);
        return wallet;
    }

//    public UserResponseModel uploadProfilePicture(Long id, String pictureUrl) {
//        User user = userRepository.findById(id).orElseThrow();
//        user.setProfilePicture(pictureUrl);
//        userRepository.save(user);
//        return UserResponseModel.builder().user(user).build();
//    }

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
            throw new VerifyException("password invalid");
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

    public UserResponseModel searchByNationalCode(String nationalCode) {
        User user = userRepository.findByNationalCode(nationalCode).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseModel.builder().user(user).build();
    }

    public UserResponseModel searchByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
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

//
//    public UserResponseModel searchByRole(String role) {
//        User user = userRepository.findByRole(role).orElseThrow();
//        return UserResponseModel.builder().user(user).build();
//    }

}
