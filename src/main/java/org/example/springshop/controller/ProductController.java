package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.ProductRequestModel;
import org.example.springshop.model.dto.responsemodel.ProductResponseModel;
import org.example.springshop.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ProductResponseModel> listProduct() {
        return productService.listProduct();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ProductResponseModel addProduct(@RequestBody ProductRequestModel productRequestModel) {
        return productService.addProduct(productRequestModel);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ProductResponseModel editProduct(@PathVariable Long id, @RequestBody ProductRequestModel productRequestModel) {
        return productService.editProduct(id, productRequestModel);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @RequestMapping(value = "/search/id/{id}", method = RequestMethod.GET)
    public ProductResponseModel searchById(@PathVariable Long id) {
        return productService.searchById(id);
    }

    @RequestMapping(value = "/search/name/{name}", method = RequestMethod.GET)
    public List<ProductResponseModel> searchByName(@PathVariable String name) {
        return productService.searchByProductName(name);
    }

    @RequestMapping(value = "/search/price/{price}", method = RequestMethod.GET)
    public List<ProductResponseModel> searchByProductPrice(@PathVariable Long price) {
        return productService.searchByProductPrice(price);
    }

    @RequestMapping(value = "/search/price", method = RequestMethod.GET)
    public List<ProductResponseModel> searchByProductPriceBetween(@RequestParam Long minPrice, @RequestParam Long maxPrice) {
        return productService.searchByPriceBetween(minPrice, maxPrice);
    }

    @RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
    public List<ProductResponseModel> listCategory(@PathVariable Long category) {
        return productService.listByCategory(category);
    }
}
