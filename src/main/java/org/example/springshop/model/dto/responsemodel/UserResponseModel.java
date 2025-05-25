package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.User;
import org.example.springshop.model.UserRole;
import org.example.springshop.model.Wallet;

@NoArgsConstructor
@Getter
public class UserResponseModel {

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

    @Builder
    public UserResponseModel(User user ) {
        this.id = user.getId();
        this.name = user.getName();
        this.password= user.getPassword();
        this.wallet = user.getWallet().getId();
        this.userRole= user.getRole();
    }
}
