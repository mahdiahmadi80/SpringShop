package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.ProductRequestModel;
import org.springframework.stereotype.Component;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name = "TBL_PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "PRODUCT_PRICE")
    private Long productPrice;
    @Column(name = "PRODUCT_EXIST")
    private Long productExist;


    @Builder
    public Product(ProductRequestModel request) {
        this.productName = request.getProductName();
        this.productPrice = request.getProductPrice();
        this.productExist = request.getProductExist();
    }
}
