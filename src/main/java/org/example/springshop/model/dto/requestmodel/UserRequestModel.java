package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.UserRole;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestModel {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lastname")
    private Optional<String> lastName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private Optional<String> email;
    @JsonProperty("phoneNumber")
    private Optional<String> phoneNumber;
    @JsonProperty("nationalCode")
    private Optional<String> nationalcode ; ;
    @JsonProperty("profilePicture")
    private Optional<String> profilePicture;
    @JsonProperty("user_role")
    private UserRole userRole;
    @JsonProperty("addressId")
    private Long addressId;

}
