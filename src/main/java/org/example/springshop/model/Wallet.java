package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.WalletRequestModel;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User userId;
    @Column(name = "BALANCE")
    private Long balance;


    @Builder
    public Wallet(WalletRequestModel walletRequestModel, User user) {
        this.id = walletRequestModel.getId();
        this.balance = walletRequestModel.getBalance();
        this.userId = user;
    }
}
