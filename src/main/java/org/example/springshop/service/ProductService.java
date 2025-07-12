package org.example.springshop.service;

import org.example.springshop.exception.productException.ProductNotExist;
import org.example.springshop.exception.productException.ProductValueException;
import org.example.springshop.model.Category;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.ProductRequestModel;
import org.example.springshop.model.dto.responsemodel.ProductResponseModel;
import org.example.springshop.repository.CategoryRepository;
import org.example.springshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponseModel> listProduct() {
        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
            productResponseModels.add(productResponseModel);
        });
        return productResponseModels;
    }

    public ProductResponseModel addProduct(ProductRequestModel requestModel) {
        Category category = categoryRepository.findById(requestModel.getCategory()).orElseThrow();

        Product newProduct;
        try {
            newProduct = Product.productBuilder().request(requestModel).category(category).build();
        } catch (ProductValueException e) {
            throw new ProductValueException("value is false");
        }
        productRepository.save(newProduct);
        return ProductResponseModel.builder().product(newProduct).build();
    }

    public ProductResponseModel editProduct(Long id, ProductRequestModel productRequestModel) {
        Product updateProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotExist("product not found"));
        updateProduct.setName(productRequestModel.getName());

        if (productRequestModel.getInventory() != null) {
            updateProduct.setInventory(productRequestModel.getInventory());
        }
        updateProduct.setInventory(updateProduct.getInventory());
        if (productRequestModel.getPrice() != null) {
            updateProduct.setPrice(productRequestModel.getPrice());
        }
        updateProduct.setPrice(updateProduct.getPrice());
        productRepository.save(updateProduct);
        return ProductResponseModel.builder().product(updateProduct).build();
    }

    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "product is deleted";
    }

    public ProductResponseModel searchById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return ProductResponseModel.builder().product(product).build();
    }

    public List<ProductResponseModel> searchByProductName(String name) {
        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        productRepository.searchByProductName(name).forEach(product -> {
            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
            productResponseModels.add(productResponseModel);
        });
        return productResponseModels;
    }

    public List<ProductResponseModel> searchByProductPrice(Long price) {
        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        productRepository.searchByProductPrice(price).forEach(product -> {
            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
            productResponseModels.add(productResponseModel);
        });
        return productResponseModels;
    }

    public List<ProductResponseModel> searchByPriceBetween(Long minPrice, Long maxPrice) {
        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        productRepository.searchByPriceBetween(minPrice, maxPrice).forEach(product -> {
            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
            productResponseModels.add(productResponseModel);
        });
        return productResponseModels;
    }
//    public List<ProductResponseModel> listByCategory(String category) {
//        List<ProductResponseModel> productResponseModels = new ArrayList<>();
//        productRepository.listByCategory(category).forEach(product -> {
//            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
//            productResponseModels.add(productResponseModel);
//        });
//        return productResponseModels;
//    }
}
