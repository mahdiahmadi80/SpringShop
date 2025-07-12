package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Comment;

@Getter
@NoArgsConstructor
public class CommentResponseModel {
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("product_id")
    private Long product_id;
    @JsonProperty("user_id")
    private Long user_id;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("userName")
    private String userName;

    @Builder

    public CommentResponseModel(Comment comment) {
        this.comment = comment.getComment();
        this.product_id = comment.getProduct().getId();
        this.user_id = comment.getUser().getId();
        this.productName = comment.getProduct().getName();
        this.userName = comment.getUser().getName();
    }
}
