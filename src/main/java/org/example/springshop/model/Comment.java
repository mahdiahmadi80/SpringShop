package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.CommentRequestModel;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TBL_COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "COMMENT")
    private String comment;
    @Column(name = "STAR")
    private Long star;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder(builderClassName = "CommentClass", builderMethodName = "commentBuilder")
    public Comment(CommentRequestModel commentRequestModel, Product product, User user) {
        this.comment = commentRequestModel.getComment();
        this.star = commentRequestModel.getStar();
        this.product = product;
        this.user = user;
    }
}
