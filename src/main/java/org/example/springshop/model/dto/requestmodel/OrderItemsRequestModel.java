package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderItemsRequestModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("order_id")
    private Long orderId;

}
