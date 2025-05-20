package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.WalletRequestModel;
import org.example.springshop.model.dto.UserRequestModel;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name = "TBL_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PASSWORD")
    private String password;
    @OneToOne
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    @Column(name = "USER_ROLE")
    private UserRole role;

    //    @JsonIgnore
//    @OneToMany
//    private List<Order> orders = new ArrayList<>();
    //    @Builder
//    public User(UserRequestModel userRequestModel, WalletRequestModel walletRequestModel) {
//
//        this.name = userRequestModel.getName();
//        this.password = userRequestModel.getPassword();
//        this.wallet = Wallet.builder().walletRequestModel(walletRequestModel).build();
//    }
    @Builder
    public User(UserRequestModel userRequestModel, Wallet wallet, UserRole userRole) {
        this.name = userRequestModel.getName();
        this.password = userRequestModel.getPassword();
        this.wallet = wallet;
        this.role = userRole;
    }

}
