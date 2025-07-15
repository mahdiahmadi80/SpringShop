package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.CartItemsRequestModel;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TBL_CARTITEMS")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @Column(name = "QUANTITY")
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Builder(builderClassName = "CartItemsClass", builderMethodName = "cartItemsBuilder")
    public CartItems(CartItemsRequestModel cartItemsRequestModel, Product product, Cart cart) {
        this.product = product;
        this.quantity = cartItemsRequestModel.getQuantity();
//        this.cart = cart;
    }
}


