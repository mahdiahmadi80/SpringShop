package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "TBL_CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime CreatedAt;
    @UpdateTimestamp
    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;

    @Builder(builderClassName = "CartBuilderClass", builderMethodName = "cartBuilder")

    public Cart(User user) {
        this.user = user;
    }
}
