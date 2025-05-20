package org.example.springshop.service;

import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.UserRequestModel;
import org.example.springshop.model.dto.WalletRequestModel;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;

    private WalletRequestModel walletRequestModel;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public List<User> userList() {
        return userRepository.findAll();
    }

    public User userAdd(UserRequestModel userRequestModel) {

        userRequestModel.setPassword(encoder.encode(userRequestModel.getPassword()));

        Wallet wallet = new Wallet();
        User user = User.builder().userRequestModel(userRequestModel).wallet(wallet).build();
        wallet.setUserId(userRequestModel.getId());
        wallet.setBalance(0L);

        walletRepository.save(wallet);
        return userRepository.save(user);
    }

    public void userDelete(Long id) {
        userRepository.deleteById(id);
    }

    public String verify(UserRequestModel userRequestModel) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestModel.getName(), userRequestModel.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userRequestModel.getName());
        }
        return "fail";
    }
}
