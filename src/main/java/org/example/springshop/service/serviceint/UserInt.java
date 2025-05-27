package org.example.springshop.service.serviceint;

import org.example.springshop.model.User;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;

import java.util.List;

public interface UserInt {

    List<UserResponseModel> userList();

    UserResponseModel userSearch(Long id);

    User userAdd(UserRequestModel userRequestModel);

    void userDelete(Long id);

    String verify(UserRequestModel userRequestModel);
//User findById(Long id);
}
