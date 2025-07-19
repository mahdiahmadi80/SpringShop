package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Order;
import org.example.springshop.model.OrderItems;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseModel {

    @JsonProperty("Id")
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("totalAmount")
    private Long totalAmount;
    @JsonProperty("orderItems")
    private List<OrderItems> orderItems;
    @JsonProperty("paymentDate")
    private LocalDateTime paymentDate;

    @Builder
    public OrderResponseModel(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.totalAmount = order.getTotalAmount();
        this.userName = order.getUser().getName();
        this.orderItems = order.getOrderItems();
        this.paymentDate = order.getPaymentAt();
    }
}
