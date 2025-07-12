package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.CartItems;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestModel {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("cartItems")
    private List<CartItems> cartItems;
}
