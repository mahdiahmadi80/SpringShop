package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Category;
import org.example.springshop.model.Product;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryResponseModel {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("product_list")
    private List<Product> products;

    @Builder
    public CategoryResponseModel(Category category) {
        this.name = category.getName();
        this.description = category.getDescription();
        this.products = category.getProducts();

    }
}
