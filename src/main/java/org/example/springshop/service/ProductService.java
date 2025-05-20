package org.example.springshop.service;

import org.example.springshop.model.Product;
import org.example.springshop.model.dto.ProductRequestModel;
import org.example.springshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductInt {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> productList() {
        return productRepository.findAll();
    }
    @Override
    public Product addProduct(ProductRequestModel requestModel) {
        Product newProduct = Product.builder().request(requestModel).build();
        return productRepository.save(newProduct);
    }
    @Override
    public Product editProduct(Long id, ProductRequestModel productRequestModel) {
        Product oldProduct = productRepository.findById(id).orElseThrow();
        oldProduct.setProductName(productRequestModel.getProductName());
        oldProduct.setProductPrice(productRequestModel.getProductPrice());
        oldProduct.setProductExist(productRequestModel.getProductExist());
        return productRepository.save(oldProduct);
    }
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
