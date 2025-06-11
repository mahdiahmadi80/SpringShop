package org.example.springshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.requestmodel.UserRequestModelSignUp;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
import org.example.springshop.model.dto.responsemodel.UserResponseModelsignUp;
import org.example.springshop.service.UserService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserResponseModel> listUser() {
        return userService.listUser();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public UserResponseModel addUser(@RequestBody UserRequestModel userRequestModel) {
        return userService.addUser(userRequestModel);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public UserResponseModel editUser(@PathVariable Long id, @RequestBody UserRequestModel userRequestModel) {
        return userService.editUser(id, userRequestModel);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "user is deleted";
    }

    @RequestMapping(value = "/signup/{id}", method = RequestMethod.POST)
    public UserResponseModelsignUp signupUser(@PathVariable Long id, @RequestBody UserRequestModelSignUp userRequestModelSignUp) {
        return userService.signUpUser(id, userRequestModelSignUp);
    }

    @RequestMapping(value = ("/{id}"), method = RequestMethod.GET)
    public User searchUser(@PathVariable Long id) {
        return userService.searchUser(id);
    }

    @RequestMapping(value = "/csrf-token", method = RequestMethod.GET)
    public CsrfToken getcsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserRequestModel userRequestModel) {
        return userService.verify(userRequestModel);
    }
}
