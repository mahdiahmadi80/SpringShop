package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.UserRole;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestModel {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("nationalCode")
    private String nationalcode;
    @JsonProperty("profilePicture")
    private String profilePicture;
    @JsonProperty("user_role")
    private UserRole userRole;
    @JsonProperty("addressId")
    private Long addressId;
}
