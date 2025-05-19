package org.example.springshop.service;

import org.example.springshop.model.Product;
import org.example.springshop.model.dto.ProductRequestModel;

import java.util.List;

public interface ProductInt {
    List<Product> productList();

    Product addProduct(ProductRequestModel requestModel);

    Product editProduct(Long id, ProductRequestModel productRequestModel);

    void deleteProduct(Long id);
}
