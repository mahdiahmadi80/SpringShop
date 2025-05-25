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
    private String productName;
    @JsonProperty("product_price")
    private Long productPrice;
    @JsonProperty("product_exist")
    private Long productExist;

    @Builder
    public ProductResponseModel(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productExist = product.getProductExist();
    }
}
