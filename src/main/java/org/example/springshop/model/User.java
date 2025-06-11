package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.requestmodel.UserRequestModelSignUp;

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
    @Column(name = "PHONENUMBER")
    private Long phoneNumber;
    @Column(name = "NATIONALCODE")
    private Long nationalCode;
    @OneToOne
    @JoinColumn(name = "WALLET_ID")
    private Wallet wallet;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_ROLE")
    private UserRole role;
    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Builder(builderClassName = "UserClass", builderMethodName = "userBuilder")
    public User(UserRequestModel userRequestModel, UserRole userRole) {
        this.name = userRequestModel.getName();
        this.password = userRequestModel.getPassword();
        this.role = userRequestModel.getUserRole();

    }

    @Builder(builderClassName = "UserClass", builderMethodName = "userBuilderSignUp")
    public User(UserRequestModelSignUp userRequestModelSignUp, Wallet wallet, Address address) {
        this.lastName = userRequestModelSignUp.getLastName();
        this.phoneNumber = userRequestModelSignUp.getPhoneNumber();
        this.nationalCode = userRequestModelSignUp.getNationalCode();
        this.wallet = wallet;
        this.address = userRequestModelSignUp.getAddress();
    }

}
//FOUNDATIONS OF PROGRAMMING: FUNDAMENTALS
//FOUNDATIONS OF PROGRAMMING: OBJECT-ORIENTED DESIGN
//FOUNDATIONS OF PROGRAMMING: DATA STRUCTURES
//FOUNDATIONS OF PROGRAMMING: REFACTORING CODE
//FOUNDATIONS OF PROGRAMMING: CODE EFFICIENCY
//FOUNDATIONS OF PROGRAMMING: DATABASES
//FOUNDATIONS OF PROGRAMMING: TEST-DRIVEN DEVELOPMENT

