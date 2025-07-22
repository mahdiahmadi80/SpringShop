package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("star")
    private Long star;
    @JsonProperty("product_id")
    private Long product_id;
    @JsonProperty("user_id")
    private Long user_id;
}
