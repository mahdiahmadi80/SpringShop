package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_USER")
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_ROLE")
    private UserRole role;
    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Builder(builderClassName = "UserClass", builderMethodName = "userBuilder")
    public User(UserRequestModel userRequestModel) {
        this.name = userRequestModel.getName();
        this.lastName = userRequestModel.getLastName();
        this.password = userRequestModel.getPassword();
        this.email = userRequestModel.getEmail();
        this.phoneNumber = userRequestModel.getPhoneNumber();
        this.nationalCode = userRequestModel.getNationalCode();
        this.profilePicture = userRequestModel.getProfilePicture();
        this.role = userRequestModel.getUserRole();
    }
}


