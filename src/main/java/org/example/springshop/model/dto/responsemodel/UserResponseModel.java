package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.User;
import org.example.springshop.model.UserRole;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class UserResponseModel {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lastname")
    private Optional<String> lastName;
    @JsonProperty("password")
    private Optional<String> password;
    @JsonProperty("email")
    private Optional<String> email;
    @JsonProperty("phoneNumber")
    private Optional<String> phoneNumber;
    @JsonProperty("nationalCode")
    private Optional<String> nationalCode;
    @JsonProperty("profilePicture")
    private Optional<String> profilePicture;
    @JsonProperty("user_role")
    private UserRole userRole;

    @Builder
    public UserResponseModel(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = Optional.ofNullable(user.getLastName());
        this.password = Optional.ofNullable(user.getPassword());
        this.email = Optional.ofNullable(user.getEmail());
        this.phoneNumber = Optional.ofNullable(user.getPhoneNumber());
        this.nationalCode = Optional.ofNullable(user.getNationalCode());
        this.profilePicture = Optional.ofNullable(user.getProfilePicture());
        this.userRole = user.getRole();
    }
}
