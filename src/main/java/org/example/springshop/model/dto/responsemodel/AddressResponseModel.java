package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.springshop.model.Address;

@Getter
@NoArgsConstructor
public class AddressResponseModel {
    @JsonProperty("id")
    private Long id;
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
        this.id = address.getId();
        this.country = address.getCountry();
        this.city = address.getCity();
        this.number = address.getNumber();
        this.postNumber = address.getPostNumber();
    }
}
