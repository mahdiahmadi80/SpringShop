package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.ProductRequestModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_PRODUCT")
@JsonIgnoreProperties("category")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PRODUCT_NAME")
    private String name;
    @Column(name = "PRODUCT_PRICE")
    private Long price;
    @Column(name = "PRODUCT_INVENTORY")
    private Long inventory;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE")
    private String image;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Builder(builderClassName = "ProductClass", builderMethodName = "productBuilder")
    public Product(ProductRequestModel request, Category category) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.inventory = request.getInventory();
        this.description = request.getDescription();
        this.image = request.getImage();
        this.category = category;
    }

    public Long backProduct(Product product, OrderItems orderItems) {
        return product.getInventory() + orderItems.getQuantity();
    }
}
