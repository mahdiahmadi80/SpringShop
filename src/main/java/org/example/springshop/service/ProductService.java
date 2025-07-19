package org.example.springshop.service;

import org.example.springshop.exception.categoryException.CategoryNotFoundException;
import org.example.springshop.exception.orderException.OrderAddFailException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.model.Category;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.ProductRequestModel;
import org.example.springshop.model.dto.responsemodel.ProductResponseModel;
import org.example.springshop.repository.CategoryRepository;
import org.example.springshop.repository.CommentRepository;
import org.example.springshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, CommentRepository commentRepository) {
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
        Category category = categoryRepository.findById(requestModel.getCategory()).orElseThrow(() -> new CategoryNotFoundException("category not found"));
        Product product = Product.productBuilder().request(requestModel).category(category).build();
        productRepository.save(product);
        return ProductResponseModel.builder().product(product).build();
    }

    public ProductResponseModel editProduct(Long id, ProductRequestModel productRequestModel) {
        Product updateProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));
        if (productRequestModel.getName() != null) {
            updateProduct.setName(productRequestModel.getName());
        }
        updateProduct.setName(updateProduct.getName());
        if (productRequestModel.getInventory() != null) {
            updateProduct.setInventory(productRequestModel.getInventory());
        }
        updateProduct.setInventory(updateProduct.getInventory());
        if (productRequestModel.getPrice() != null) {
            updateProduct.setPrice(productRequestModel.getPrice());
        }
        updateProduct.setPrice(updateProduct.getPrice());
        if (productRequestModel.getDescription() != null) {
            updateProduct.setDescription(productRequestModel.getDescription());
        }
        updateProduct.setDescription(updateProduct.getDescription());
        productRepository.save(updateProduct);
        return ProductResponseModel.builder().product(updateProduct).build();
    }

    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "product is deleted";
    }

    public Long itemPrice(Product product, Long quantity) {
        return product.getPrice() * quantity;
    }

    public void checkQuantity(Product product, Long quantity) {
        if (quantity > product.getInventory()) {
            throw new ProductNotFoundException("your count is over than inventory");
        }
    }

    public void updateQuantity(Product product, Long count) {
        product.setInventory(product.getInventory() - count);
        if (product.getInventory() < 0) {
            throw new OrderAddFailException("product not enough");
        }
        productRepository.save(product);
    }



    public ProductResponseModel searchById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));
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

    public List<ProductResponseModel> listByCategory(Long category) {
        List<ProductResponseModel> productResponseModels = new ArrayList<>();
        productRepository.findByCategoryId(category).forEach(product -> {
            ProductResponseModel productResponseModel = ProductResponseModel.builder().product(product).build();
            productResponseModels.add(productResponseModel);
        });
        return productResponseModels;
    }
}
