package org.example.springshop.controller;

import jakarta.servlet.http.HttpServletRequest;
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
        return userService.deleteUser(id);
    }

    @RequestMapping(value = "/signup/{id}", method = RequestMethod.POST)
    public UserResponseModel signupUser(@PathVariable Long id, @RequestBody UserRequestModel userRequestModel) {
        return userService.signUpUser(id, userRequestModel);
    }

    @RequestMapping(value = "/search/id/{id}", method = RequestMethod.GET)
    public UserResponseModel searchUserById(@PathVariable Long id) {
        return userService.searchUserById(id);
    }

    @RequestMapping(value = "/search/nationalcode/{nationalCode}", method = RequestMethod.GET)
    public UserResponseModel searchUserByNationalCode(@PathVariable String nationalCode) {
        return userService.searchByNationalCode(nationalCode);
    }
    @RequestMapping(value = "/search/phonenumber/{phoneNumber}", method = RequestMethod.GET)
    public UserResponseModel searchUserByPhoneNumber(@PathVariable String phoneNumber) {
        return userService.searchByPhoneNumber(phoneNumber);
    }

    @RequestMapping(value = "/search/email/{email}", method = RequestMethod.GET)
    public UserResponseModel searchByEmail(@PathVariable String email) {
        return userService.searchByEmail(email);
    }

    @RequestMapping(value = "/search/role/{role}", method = RequestMethod.GET)
    public List<UserResponseModel> searchByRole(@PathVariable Long role) {
        return userService.searchUserByRole(role);
    }

    @RequestMapping(value = "/profile/{id}")
    public UserResponseModel uploadProfilePicture(@PathVariable Long id, @RequestBody String pictureUrl) {
        return userService.uploadProfilePicture(id, pictureUrl);
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
