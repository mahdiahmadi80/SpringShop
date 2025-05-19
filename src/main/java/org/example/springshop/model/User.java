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
    @Column(name = "USER_ROLE")
    private UserRole role;

    @JsonIgnore
    @OneToMany
    private List<Order> orders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    //    @Builder
//    public User(UserRequestModel userRequestModel, WalletRequestModel walletRequestModel) {
//
//        this.name = userRequestModel.getName();
//        this.password = userRequestModel.getPassword();
//        this.wallet = Wallet.builder().walletRequestModel(walletRequestModel).build();
//    }
    @Builder
    public User(UserRequestModel userRequestModel, WalletRequestModel walletRequestModel) {
        this.name = userRequestModel.getName();
        this.password = userRequestModel.getPassword();
        Wallet wallet1 = Wallet.builder().walletRequestModel(walletRequestModel).build();
        this.wallet = wallet1;
    }

}
