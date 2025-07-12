package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
@JsonProperty("orderItem")
    private List<OrderItemsRequestModel> orderItems;

//    @JsonProperty("product_id")
//    private Long productId;
//    @JsonProperty("product_count")
//    private Long count;
}
