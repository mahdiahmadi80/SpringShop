package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Address;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseModel {
    @JsonProperty("country")
    private String country;
    @JsonProperty("city")
    private String city;
    @JsonProperty("number")
    private Long number;
    @JsonProperty("postnumber")
    private Long postNumber;

    @Builder

    public AddressResponseModel(Address address) {
        this.country = address.getCountry();
        this.city = address.getCity();
        this.number = address.getNumber();
        this.postNumber = address.getPostNumber();
    }
}
