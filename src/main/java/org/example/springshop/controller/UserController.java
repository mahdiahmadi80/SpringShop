package org.example.springshop.controller;

import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.UserRequestModel;
import org.example.springshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.userList();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User userAdd(@RequestBody UserRequestModel userRequestModel) {


        return userService.userAdd(User
                .builder().userRequestModel(userRequestModel).build());
    }

    @DeleteMapping(value = "/delete/{id}")
    public void userDelete(@PathVariable Long id) {
        userService.userDelete(id);
    }
}
