package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Comment;
import org.example.springshop.model.Product;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Long productPrice;
    @JsonProperty("inventory")
    private Long inventory;
    @JsonProperty("description")
    private String description;
    @JsonProperty("image")
    private String image;

    @Builder
    public ProductResponseModel(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.productPrice = product.getPrice();
        this.inventory = product.getInventory();
        this.description = product.getDescription();
        this.image = product.getImage();

    }
}
