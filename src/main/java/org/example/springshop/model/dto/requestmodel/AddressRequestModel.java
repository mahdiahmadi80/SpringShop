package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("country")
    private String country;
    @JsonProperty("city")
    private String city;
    @JsonProperty("number")
    private Long number;
    @JsonProperty("postNumber")
    private Long postNumber;
    @JsonProperty("user_id")
    private Long userId;
}
