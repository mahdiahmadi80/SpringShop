package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.FavoriteItem;
import org.example.springshop.model.Product;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class FavoriteItemResponseModel {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("product_id")
    private Product productId;

    @Builder
    public FavoriteItemResponseModel(FavoriteItem favoriteItem) {
        this.userId = favoriteItem.getUser().getId();
//this.productId = favoriteItem.getProduct();
    }
}
