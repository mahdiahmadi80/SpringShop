package org.example.springshop.service;

import org.example.springshop.exception.productException.ProductNotExist;
import org.example.springshop.exception.productException.ProductValueException;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.ProductRequestModel;
import org.example.springshop.model.dto.responsemodel.ProductResponseModel;
import org.example.springshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<ProductResponseModel> productList() {
        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
            productResponseModels.add(productResponseModel);
        });
        return productResponseModels;
    }

    public ProductResponseModel addProduct(ProductRequestModel requestModel) {
        Product newProduct;
        try {
            newProduct = Product.productBuilder().request(requestModel).build();
        } catch (ProductValueException e) {
            throw new ProductValueException("value is false");
        }
        productRepository.save(newProduct);
        return ProductResponseModel.builder().product(newProduct).build();
    }

    public ProductResponseModel editProduct(Long id, ProductRequestModel productRequestModel) {
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotExist("product not found"));
        oldProduct.setName(productRequestModel.getName());
        oldProduct.setPrice(productRequestModel.getPrice());
        oldProduct.setInventory(productRequestModel.getInventory());
        productRepository.save(oldProduct);
        return ProductResponseModel.builder().product(oldProduct).build();
    }

    public String deleteProduct(Long id) {


        productRepository.deleteById(id);

        return "product is deleted";
    }
}
