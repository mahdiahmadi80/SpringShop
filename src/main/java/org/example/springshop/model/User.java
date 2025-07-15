package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;

import java.util.List;

//@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_USER")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "NATIONAL_CODE")
    private String nationalCode;
    @Column(name = "PROFILE_PICTURE")
    private String profilePicture;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private Wallet wallet;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_ROLE")
    private UserRole role;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FavoriteItem> favoriteItem;
//    private Order order;
//    private Cart cart;
//    @Column(name = "Test")
//    private String test ;

    @Builder(builderClassName = "UserClass", builderMethodName = "userBuilder")
    public User(UserRequestModel userRequestModel, Address address) {
        this.name = userRequestModel.getName();
        this.lastName = String.valueOf(userRequestModel.getLastName());
        this.password = userRequestModel.getPassword();
        this.email = String.valueOf(userRequestModel.getEmail());
        this.phoneNumber = userRequestModel.getPhoneNumber().orElse(null);
        this.nationalCode = String.valueOf(userRequestModel.getNationalcode());
        this.profilePicture = String.valueOf(userRequestModel.getProfilePicture());
        this.role = userRequestModel.getUserRole();
        this.address = address;
//        this.test= String.valueOf(Optional.of(test));
    }
}


