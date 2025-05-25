package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_price")
    private Long productPrice;
    @JsonProperty("product_exist")
    private Long productExist;
}
