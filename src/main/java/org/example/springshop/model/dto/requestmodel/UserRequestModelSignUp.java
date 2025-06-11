package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Address;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestModelSignUp {
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("phonenumber")
    private Long phoneNumber;
    @JsonProperty("nationalcode")
    private Long nationalCode;
    @JsonProperty("address")
    private Address address;
}
