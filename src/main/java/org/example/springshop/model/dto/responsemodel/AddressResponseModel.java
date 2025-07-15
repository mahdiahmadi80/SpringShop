package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Address;
import org.example.springshop.model.User;

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
    @JsonProperty("postNumber")
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
