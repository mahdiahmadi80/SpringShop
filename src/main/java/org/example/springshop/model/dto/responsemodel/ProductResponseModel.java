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
    @JsonProperty("comment")
    private List<Comment> comments;
//    @JsonProperty("category")
//    private String categories;
//    @JsonProperty("category_description")
//    private String categoryDescription;

    @Builder
    public ProductResponseModel(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.productPrice = product.getPrice();
        this.inventory = product.getInventory();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.comments = product.getComments();

//        this.categories = product.getCategory().getName();
//        this.categoryDescription = product.getCategory().getDescription();
//
    }
}
