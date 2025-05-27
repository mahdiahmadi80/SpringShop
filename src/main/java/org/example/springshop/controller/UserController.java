package org.example.springshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
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
    public List<UserResponseModel> userList() {
        return userService.userList();
    }

    @RequestMapping(value = ("/{id}"), method = RequestMethod.GET)
    public UserResponseModel userSearch(@PathVariable Long id) {
        return userService.userSearch(id);
    }

    @RequestMapping(value = "/csrf-token", method = RequestMethod.GET)
    public CsrfToken getcsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User userAdd(@RequestBody UserRequestModel userRequestModel) {
        return userService.userAdd(userRequestModel);

    }

    @RequestMapping(value = "/login")
    public String login(@RequestBody UserRequestModel userRequestModel) {
        return userService.verify(userRequestModel);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void userDelete(@PathVariable Long id) {
        userService.userDelete(id);
    }
}
