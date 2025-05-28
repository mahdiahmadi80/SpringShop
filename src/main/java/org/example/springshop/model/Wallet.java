package org.example.springshop.model;

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

    @Builder(builderMethodName = "userWalletClass", builderClassName = "UserWalletClass")
    public Wallet(User user) {
        this.balance = 0L;
        this.userId = user;
    }
}
