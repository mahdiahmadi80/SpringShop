package org.example.springshop.service;

import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.UserRequestModel;
import org.example.springshop.model.dto.WalletRequestModel;
import org.example.springshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public List<User> userList() {
        return userRepository.findAll();
    }

    public User userAdd(UserRequestModel userRequestModel) {


        userRequestModel.setPassword(encoder.encode(userRequestModel.getPassword()));


        User user = User.builder().userRequestModel(userRequestModel).build();

//        Wallet wallet = Wallet.builder().walletRequestModel(userRequestModel).build();

//        User user = User.builder().userRequestModel(userRequestModel).walletRequestModel(wallet).build();

        return userRepository.save(user);
    }

    public void userDelete(Long id) {
        userRepository.deleteById(id);
    }
}
