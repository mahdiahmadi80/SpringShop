package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Order;

@Getter
@NoArgsConstructor
public class OrderResponseModel {

    @JsonProperty("Id")
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_name")
    private String productName;

    @Builder
    public OrderResponseModel(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.userName = order.getUser().getName();
        this.productId = order.getProduct().getId();
        this.productName = order.getProduct().getProductName();
    }
}
