package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.FavoriteItem;

@Getter
@NoArgsConstructor
public class FavoriteItemResponseModel {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("product_id")
    private Long productId;

    @Builder
    public FavoriteItemResponseModel(FavoriteItem favoriteItem) {
        this.userId = favoriteItem.getUser().getId();
        this.productId = favoriteItem.getProduct().getId();
    }
}
