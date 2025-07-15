package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Column(name = "TOTALAMOUNT")
    private Long totalAmount;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItems> orderItems;

    @Builder(builderClassName = "OrderClass", builderMethodName = "orderBuilder")
    public Order(User user, Long totalAmount, List<OrderItems> orderItems) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }
}
