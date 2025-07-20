package org.example.springshop.model.dto.responsemodel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Cart;
import org.example.springshop.model.CartItems;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class CartResponseModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private Long user;
//    @JsonProperty("product")
//    private Optional<String> product;
//    @JsonProperty("quantity")
//    private Optional<Long> quantity;

    @Builder
    public CartResponseModel(Cart cart) {
        this.id = cart.getId();
        this.user = cart.getUser().getId();
//        this.product = Optional.ofNullable(cartItems.getProduct().getName());
//        this.quantity = Optional.ofNullable(cartItems.getQuantity());
    }
}
