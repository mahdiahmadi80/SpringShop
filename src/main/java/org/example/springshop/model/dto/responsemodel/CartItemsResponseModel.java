package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.CartItems;

@Getter
@NoArgsConstructor
public class CartItemsResponseModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("cart_id")
    private Long cart;

    @Builder

    public CartItemsResponseModel(CartItems cartItems) {
        this.id = cartItems.getId();
        this.quantity = cartItems.getQuantity();
        this.productId = cartItems.getProduct().getId();
        this.cart = cartItems.getCart().getId();
    }
}
