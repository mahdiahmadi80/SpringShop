package org.example.springshop.controller;

import org.example.springshop.model.Product;
import org.example.springshop.model.dto.ProductRequestModel;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Product> productList() {
        return productService.productList();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Product productAdd(@RequestBody ProductRequestModel productRequestModel) {
        return productService.addProduct(productRequestModel);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public Product productEdit(@PathVariable Long id, @RequestBody ProductRequestModel productRequestModel) {
        return productService.editProduct(id, productRequestModel);
    }

    @DeleteMapping(value = "delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
