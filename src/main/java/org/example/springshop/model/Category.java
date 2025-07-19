package org.example.springshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.CategoryRequestModel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "TBL_CATEGORY")
@JsonIgnoreProperties("products")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;


    @Builder(builderClassName = "CategoryClass", builderMethodName = "categoryBuilder")
    public Category(CategoryRequestModel categoryRequestModel, List<Product> products) {
        this.name = categoryRequestModel.getName();
        this.description = categoryRequestModel.getDescription();

    }
}
