package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.springshop.model.Order;
import org.example.springshop.model.OrderItems;
import org.example.springshop.model.Product;
@Getter
@NoArgsConstructor
public class OrderItemsResponseModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("product_name")
    private String productName;


    @Builder
    public OrderItemsResponseModel(OrderItems orderItems, Product product, Order order) {
        this.id = orderItems.getId();
        this.quantity = orderItems.getQuantity();
        this.amount = orderItems.getAmount();
        this.productName = product.getName();
//        this.order = order;
    }
}
