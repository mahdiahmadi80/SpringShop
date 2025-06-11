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
    public List<ProductResponseModel> productList() {
        return productService.productList();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ProductResponseModel productAdd(@RequestBody ProductRequestModel productRequestModel) {
        return productService.addProduct(productRequestModel);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ProductResponseModel productEdit(@PathVariable Long id, @RequestBody ProductRequestModel productRequestModel) {
        return productService.editProduct(id, productRequestModel);
    }

    @DeleteMapping(value = "delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
