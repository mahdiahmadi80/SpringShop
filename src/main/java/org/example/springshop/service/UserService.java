package org.example.springshop.service;

import org.example.springshop.model.User;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;

import org.example.springshop.repository.UserRepository;
import org.example.springshop.repository.WalletRepository;
import org.example.springshop.service.serviceint.UserInt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserInt {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    //for MD5
    public static String generateMD5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public List<UserResponseModel> userList() {
        List<UserResponseModel> userResponseModels = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
                    UserResponseModel userResponseModel = UserResponseModel.builder().user(user).build();
                    userResponseModels.add(userResponseModel);
                }
        );
        return userResponseModels;
    }

    public User userAdd(UserRequestModel userRequestModel) {

        String hashedPassword = UserService.generateMD5Hash(userRequestModel.getPassword());
        userRequestModel.setPassword(hashedPassword);

//        userRequestModel.setPassword(encoder.encode(userRequestModel.getPassword()));

        User user = User.builder().userRequestModel(userRequestModel).userRole(userRequestModel.getUserRole()).build();

        user = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUserId(user);
        wallet.setBalance(0L);
        walletRepository.save(wallet);

        user.setWallet(wallet);

        return userRepository.save(user);
    }

    public void userDelete(Long id) {
        userRepository.deleteById(id);
    }


    public String verify(UserRequestModel userRequestModel) {

//        Authentication authentication =
//                authManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestModel.getName(), userRequestModel.getPassword()));

        String hashedPassword = UserService.generateMD5Hash(userRequestModel.getPassword());
        User userPass = userRepository.findByName(userRequestModel.getName());
        if (hashedPassword.equals(userPass.getPassword())) {
            return jwtService.generateToken(userRequestModel.getName());
        }
        return "fail";
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
