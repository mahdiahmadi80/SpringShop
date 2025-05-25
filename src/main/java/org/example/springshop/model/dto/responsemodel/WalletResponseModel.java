package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Wallet;

@Getter
@NoArgsConstructor
public class WalletResponseModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("balance")
    private Long balance;

    @Builder
    public WalletResponseModel(Wallet wallet) {
        this.id = wallet.getId();
        this.userId = wallet.getUserId().getId();
        this.userName = wallet.getUserId().getName();
        this.balance = wallet.getBalance();
    }
}
