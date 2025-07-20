package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.CartRequestModel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TBL_CARTITEMS")
@JsonIgnoreProperties("cart")
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
    public CartItems(Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
    }
}


