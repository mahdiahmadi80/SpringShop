package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.User;
import org.example.springshop.model.UserRole;
import org.example.springshop.model.Wallet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModelsignUp {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("LASTNAME")
    private String lastName;
    @JsonProperty("PASSWORD")
    private String password;
    @JsonProperty("PHONENUMBER")
    private Long phoneNumber;
    @JsonProperty("WALLET_ID")
    private Wallet wallet;
    @JsonProperty("USER_ROLE")
    private UserRole role;

    @Builder
    public UserResponseModelsignUp(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.wallet = user.getWallet();
        this.role = user.getRole();
    }

}
