package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;

@Data
//@Getter
//@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_ORDERITEMS")
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
    public OrderItems(OrderItemsRequestModel orderItemsRequestModel, Product product, Order order, Long quantity, Long amount) {
//        this.quantity = orderItemsRequestModel.getQuantity();
        this.quantity = quantity;
//        this.amount = orderItemsRequestModel.getAmount();
        this.amount = amount;
        this.product = product;
//        this.order = order;
    }
}
