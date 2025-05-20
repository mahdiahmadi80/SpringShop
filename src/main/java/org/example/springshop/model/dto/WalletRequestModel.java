package org.example.springshop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WalletRequestModel {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("userid")
    private Long userId;
    @JsonProperty("balance")
    private Long balance;



}
