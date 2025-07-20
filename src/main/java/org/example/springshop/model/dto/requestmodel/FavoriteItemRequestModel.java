package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoriteItemRequestModel {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("productId")
    private Long productId;
}
