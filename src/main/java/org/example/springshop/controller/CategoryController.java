package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.CategoryRequestModel;
import org.example.springshop.model.dto.responsemodel.CategoryResponseModel;
import org.example.springshop.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CategoryResponseModel> listCategory() {
        return categoryService.listCategory();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CategoryResponseModel addCategory(@RequestBody CategoryRequestModel categoryRequestModel) {
        return categoryService.addCategory(categoryRequestModel);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public CategoryResponseModel editCategory(@PathVariable Long id, @RequestBody CategoryRequestModel categoryRequestModel) {
        return categoryService.editCategory(id, categoryRequestModel);
    }
//    @RequestMapping(value = "/search/{category}")
//    public List<CategoryResponseModel> productOfCategory(@PathVariable String category){
//        return categoryService.listProductByCategory(category);
//    }

}
