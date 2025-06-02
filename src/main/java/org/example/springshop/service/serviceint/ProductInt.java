package org.example.springshop.service.serviceint;

import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.ProductRequestModel;
import org.example.springshop.model.dto.responsemodel.ProductResponseModel;

import java.util.List;

public interface ProductInt {
    List<ProductResponseModel> productList();

    Product addProduct(ProductRequestModel requestModel);

    Product editProduct(Long id, ProductRequestModel productRequestModel);

    void deleteProduct(Long id);
}
