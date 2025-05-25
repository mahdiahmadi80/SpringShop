package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private Long Userid;
    @JsonProperty("product_id")
    private Long productid;
}
