package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Product;

@Getter
@NoArgsConstructor
public class ProductResponseModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("product_name")
    private String name;
    @JsonProperty("product_price")
    private Long productPrice;
    @JsonProperty("inventory")
    private Long inventory;

    @Builder
    public ProductResponseModel(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.productPrice = product.getPrice();
        this.inventory = product.getInventory();
    }
}
