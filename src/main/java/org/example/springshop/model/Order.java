package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "TOTALAMOUNT")
    private Long totalAmount;
    @Column(name = "PAYMENT")
    private boolean payment;
    @Column(name = "CREATED_AT")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "PAYMENT_AT")
    private LocalDateTime paymentAt;
    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Builder(builderClassName = "OrderClass", builderMethodName = "orderBuilder")
    public Order(User user, Long totalAmount,Address address) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.payment = false;
        this.address = address;
    }
}
