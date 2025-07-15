package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Cart;
import org.example.springshop.model.CartItems;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponseModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private Long user;
    @JsonProperty("cartItems")
    private List<CartItems> cartItems;

    @Builder
    public CartResponseModel(Cart cart) {
        this.id = cart.getId();
        this.user = cart.getUser().getId();
        this.cartItems = cart.getCartItems();
    }
}
