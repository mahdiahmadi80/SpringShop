package org.example.springshop.service.serviceint;

import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.responsemodel.UserResponseModel;

import java.util.List;

public interface UserInt {

    List<UserResponseModel> userList();

    UserResponseModel userSearch(Long id);

    User userAdd(UserRequestModel userRequestModel);

    User userEdit(Long id, UserRequestModel userRequestModel);

    void userDelete(Long id);

    String verify(UserRequestModel userRequestModel);
//User findById(Long id);
}
