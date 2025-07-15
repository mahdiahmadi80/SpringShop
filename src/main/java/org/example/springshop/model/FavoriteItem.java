package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToMany
    private List<Product> product;

    @Builder(builderClassName = "FavoriteClass", builderMethodName = "favoriteBuilder")
    public FavoriteItem(User user, List<Product> product) {
        this.user = user;
        this.product = product;
    }
}