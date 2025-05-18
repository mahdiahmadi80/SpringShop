package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.BuyRequestModel;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_BUY")
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
//
//    @Builder
//    public Buy(BuyRequestModel buyRequestModel) {
//        this.id = buyRequestModel.getId();
//        this.user = buyRequestModel.getUserid();
//        this.product = buyRequestModel.getProductid();
//    }
}
