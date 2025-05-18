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
    @Column(name = "ACCOUNTNUMBER")
    private Long accountNumber;
    @Column(name = "BALANCE")
    private Long balance;


    @Builder
    public Wallet(WalletRequestModel accountRequestModel) {
        this.id = accountRequestModel.getId();
        this.accountNumber = accountRequestModel.getAccountNumber();
        this.balance = accountRequestModel.getBalance();
    }
}
