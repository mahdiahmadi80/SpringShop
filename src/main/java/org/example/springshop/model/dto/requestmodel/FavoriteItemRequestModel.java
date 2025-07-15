package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoriteItemRequestModel {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("product")
    private List<Long> product;
}
