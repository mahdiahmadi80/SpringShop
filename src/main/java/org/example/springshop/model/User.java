package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.WalletRequestModel;
import org.example.springshop.model.dto.UserRequestModel;
import org.example.springshop.model.dto.WalletRequestModel;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;

    @Builder
    public User(UserRequestModel userRequestModel, WalletRequestModel walletRequestModel) {

        this.name = userRequestModel.getName();
        this.password = userRequestModel.getPassword();
        this.wallet = Wallet.builder().accountRequestModel(walletRequestModel).build();
    }
}
