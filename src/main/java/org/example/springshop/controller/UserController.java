package org.example.springshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.UserRequestModel;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.userList();
    }


    @RequestMapping(value = "/csrf-token", method = RequestMethod.GET)
    public CsrfToken getcsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User userAdd(@RequestBody UserRequestModel userRequestModel) {

//
//        return userService.userAdd(User
//                .builder().userRequestModel(userRequestModel).build());
        return userService.userAdd(userRequestModel);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void userDelete(@PathVariable Long id) {
        userService.userDelete(id);
    }
}
