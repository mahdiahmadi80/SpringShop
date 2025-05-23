package org.example.springshop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.UserRole;
import org.example.springshop.model.Wallet;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestModel {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private String password;
    @JsonProperty("wallet")
    private Long wallet;
    @JsonProperty("user_role")
    private UserRole userRole;
}
