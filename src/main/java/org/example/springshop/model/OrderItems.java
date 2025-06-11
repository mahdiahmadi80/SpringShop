package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_orderitems")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "AMOUNT")
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

}
