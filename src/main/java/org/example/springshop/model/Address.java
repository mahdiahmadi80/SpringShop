package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.AddressRequestModel;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "CITY")
    private String city;
    @Column(name = "NUMBER")
    private Long number;
    @Column(name = "POST_NUMBER")
    private Long postNumber;
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User userId;

    @Builder(builderClassName = "AddressClass", builderMethodName = "addressBuilder")
    public Address(AddressRequestModel addressRequestModel,User user) {
        this.country = addressRequestModel.getCountry();
        this.city = addressRequestModel.getCity();
        this.number = addressRequestModel.getNumber();
        this.postNumber = addressRequestModel.getPostNumber();
        this.userId = user;
    }
}
