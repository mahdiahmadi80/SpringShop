package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_WALLET")
//@JsonIgnoreProperties("userId")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User userId;

    @Column(name = "BALANCE")
    private Long balance;

    @Builder(builderClassName = "UserWalletClass", builderMethodName = "userWalletClass")
    public Wallet(User user) {
        this.balance = 0L;
        this.userId = user;
    }
}
