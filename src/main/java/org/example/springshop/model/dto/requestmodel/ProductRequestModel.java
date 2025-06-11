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
    private String name;
    @JsonProperty("product_price")
    private Long price;
    @JsonProperty("product_inventory")
    private Long inventory;
}
