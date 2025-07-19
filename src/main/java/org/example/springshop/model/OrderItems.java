package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;

@Data

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_ORDERITEMS")
@JsonIgnoreProperties("order")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "AMOUNT")
    private Long amount;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Builder(builderClassName = "OrderItemsClass", builderMethodName = "orderItemsBuilder")
    public OrderItems(Long quantity, Product product, Order order, Long amount) {
        this.quantity = quantity;
        this.amount = amount;
        this.product = product;
        this.order = order;
    }
}
