package org.example.springshop.service;

import org.example.springshop.exception.productException.ProductNotExist;
import org.example.springshop.exception.productException.ProductValueException;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.ProductRequestModel;
import org.example.springshop.model.dto.responsemodel.ProductResponseModel;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.service.serviceint.ProductInt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements ProductInt {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseModel> productList() {
        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
            productResponseModels.add(productResponseModel);
        });


        return productResponseModels;
    }

    @Override
    public Product addProduct(ProductRequestModel requestModel) {

        Product newProduct;
        try {
            newProduct = Product.productBuilder().request(requestModel).build();
        } catch (ProductValueException e) {
            throw new ProductValueException("value is false");
        }
        return productRepository.save(newProduct);
    }

    @Override
    public Product editProduct(Long id, ProductRequestModel productRequestModel) {

        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotExist("product not found"));
        oldProduct.setName(productRequestModel.getName());
        oldProduct.setPrice(productRequestModel.getPrice());
        oldProduct.setQuantity(productRequestModel.getQuantity());

        return productRepository.save(oldProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
