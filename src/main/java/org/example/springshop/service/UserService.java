package org.example.springshop.service;

import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.service.securityservice.JWTService;
import org.example.springshop.service.serviceint.UserInt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.springshop.config.SecurityConfig.generateMD5Hash;

@Service
public class UserService implements UserInt {

    private final UserRepository userRepository;
    private final WalletService walletService;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, WalletService walletService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.walletService = walletService;
        this.jwtService = jwtService;
    }

    //for MD5


    public List<UserResponseModel> userList() {
        List<UserResponseModel> userResponseModels = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            UserResponseModel userResponseModel = UserResponseModel.builder().user(user).build();
            userResponseModels.add(userResponseModel);
        });
        return userResponseModels;
    }

    @Override
    public UserResponseModel userSearch(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseModel.builder().user(user).build();
    }

    public User userAdd(UserRequestModel userRequestModel) {
        String hashedPassword = generateMD5Hash(userRequestModel.getPassword());
        userRequestModel.setPassword(hashedPassword);

        User user = User.userBuilder().userRequestModel(userRequestModel).userRole(userRequestModel.getUserRole()).build();
        Wallet wallet = Wallet.userWalletClass().user(user).build();
        walletService.walletAdd(wallet);
        return userRepository.save(user);
    }

    public void userDelete(Long id) {
        userRepository.deleteById(id);
    }


    public String verify(UserRequestModel userRequestModel) {

        String hashedPassword = generateMD5Hash(userRequestModel.getPassword());

        User userPass = userRepository.findByName(userRequestModel.getName()).orElseThrow(() -> new UserNotFoundException("user not found"));
        if (hashedPassword.equals(userPass.getPassword())) {
            jwtService.generateToken(userRequestModel.getName());
        }


        return "welcome to site";

    }

}
