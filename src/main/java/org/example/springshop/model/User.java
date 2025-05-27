package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.springframework.stereotype.Component;

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
    @OneToOne
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_ROLE")
    private UserRole role;

    @Builder(builderClassName = "UserClass",builderMethodName ="userBuilder" )
    public User(UserRequestModel userRequestModel, Wallet wallet, UserRole userRole) {
        this.name = userRequestModel.getName();
        this.password = userRequestModel.getPassword();
        this.wallet = wallet;
        this.role = userRequestModel.getUserRole();
    }

}
