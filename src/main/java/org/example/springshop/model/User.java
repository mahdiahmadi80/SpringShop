package org.example.springshop.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "PASSWORD")
    private String password;
    @Nullable
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Nullable
    @Column(name = "NATIONAL_CODE")
    private String nationalCode;
    @Column(name = "PROFILE_PICTURE")
    private String profilePicture;
    @OneToOne
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_ROLE")
    private UserRole role;
    @OneToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

//    @Column(name = "Test")
//    private String test ;

    @Builder(builderClassName = "UserClass", builderMethodName = "userBuilder")
    public User(UserRequestModel userRequestModel, Address address) {
        this.name = userRequestModel.getName();
        this.lastName = String.valueOf(userRequestModel.getLastName());
        this.password = userRequestModel.getPassword();
        this.email = String.valueOf(userRequestModel.getEmail());
        this.phoneNumber = String.valueOf(userRequestModel.getPhoneNumber());
        this.nationalCode = String.valueOf(userRequestModel.getNationalcode());
        this.profilePicture = String.valueOf(userRequestModel.getProfilePicture());
        this.role = userRequestModel.getUserRole();
        this.address = address;
//        this.test= String.valueOf(Optional.of(test));
    }

}


