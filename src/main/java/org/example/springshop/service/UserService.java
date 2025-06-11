package org.example.springshop.service;

import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.exception.userException.VerifyException;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.requestmodel.UserRequestModelSignUp;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
import org.example.springshop.model.dto.responsemodel.UserResponseModelsignUp;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.service.securityservice.JWTService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.springshop.config.SecurityConfig.generateMD5Hash;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletService walletService;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, WalletService walletService, JWTService jwtService) {
        this.userRepository = userRepository;
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

    public UserResponseModel addUser(UserRequestModel userRequestModel) {
        String hashedPassword = generateMD5Hash(userRequestModel.getPassword());
        userRequestModel.setPassword(hashedPassword);
        User user = User.userBuilder()
                .userRequestModel(userRequestModel)
                .userRole(userRequestModel.getUserRole())
                .build();
        userRepository.save(user);
        return UserResponseModel.builder().user(user).build();
    }

    public UserResponseModelsignUp signUpUser(Long id, UserRequestModelSignUp userRequestModelSignUp) {
        User user = searchUser(id);
        user.setLastName(userRequestModelSignUp.getLastName());
        user.setPhoneNumber(userRequestModelSignUp.getPhoneNumber());
        user.setNationalCode(userRequestModelSignUp.getNationalCode());
        user.setWallet(createWallet(user));
        userRepository.save(user);
        return UserResponseModelsignUp.builder().user(user).build();
    }

    public Wallet createWallet(User user) {
        Wallet wallet = Wallet.userWalletClass().
                user(user)
                .build();
        walletService.walletAdd(wallet);
        return wallet;
    }

    public UserResponseModel editUser(Long id, UserRequestModel userRequestModel) {
        User editUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
        String hashedPassword = generateMD5Hash(userRequestModel.getPassword());
        editUser.setName(userRequestModel.getName());
        editUser.setPassword(hashedPassword);
        editUser.setRole(userRequestModel.getUserRole());
        User user = userRepository.save(editUser);
        return UserResponseModel.builder().user(user).build();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User searchUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
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
            throw new VerifyException("password invalid");
        }
        return token;
    }
}
