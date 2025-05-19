package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.OrderRequestModel;
import org.example.springshop.model.dto.ProductRequestModel;
import org.example.springshop.model.dto.UserRequestModel;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Builder
    public Order(OrderRequestModel orderRequestModel, UserRequestModel userRequestModel , ProductRequestModel productRequestModel) {
        this.id = orderRequestModel.getId();
        this.user = User.builder().userRequestModel(userRequestModel).build();
        this.product = Product.builder().request(productRequestModel).build();
    }
}
