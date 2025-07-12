package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.ProductRequestModel;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_PRODUCT")
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

    @OneToMany
    @Column(name = "COMMENTS")
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder(builderClassName = "ProductClass", builderMethodName = "productBuilder")
    public Product(ProductRequestModel request, Category category) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.inventory = request.getInventory();
        this.description = request.getDescription();
        this.comments = request.getComments();
        this.image = request.getImage();
        this.category = category;
    }
}
