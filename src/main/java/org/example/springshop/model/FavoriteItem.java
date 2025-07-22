package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_FAVORITE")
public class FavoriteItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Builder(builderClassName = "FavoriteClass", builderMethodName = "favoriteBuilder")
    public FavoriteItem(User user, Product product) {
        this.user = user;
        this.product = product;
    }
}