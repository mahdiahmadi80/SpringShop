package org.example.springshop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WalletRequestModel {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("accountnumber")
    private Long accountNumber;
    @JsonProperty("balance")
    private Long balance;

}
