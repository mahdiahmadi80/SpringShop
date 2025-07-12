package org.example.springshop.model.dto.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.Comment;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Long price;
    @JsonProperty("inventory")
    private Long inventory;
    @JsonProperty("description")
    private String description;
    @JsonProperty("image")
    private String image;
    @JsonProperty("comments")
    private List<Comment> comments;
    @JsonProperty("category")
    private Long category;
}
