package org.example.springshop.service;

import org.example.springshop.model.User;
import org.example.springshop.model.dto.UserRequestModel;

import java.util.List;

public interface UserInt {

    List<User> userList();

    User userAdd(UserRequestModel userRequestModel);

    void userDelete(Long id);

    String verify(UserRequestModel userRequestModel);

}
