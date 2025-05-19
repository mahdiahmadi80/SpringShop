package org.example.springshop.service;

import org.example.springshop.model.User;
import org.example.springshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> userList() {
        return userRepository.findAll();
    }

    public User userAdd(User user) {
        return userRepository.save(user);
    }

    public void userDelete(Long id) {
        userRepository.deleteById(id);
    }
}
