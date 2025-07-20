package org.example.springshop.service;

import org.example.springshop.exception.categoryException.CategoryNotFoundException;
import org.example.springshop.model.Category;
import org.example.springshop.model.dto.requestmodel.CategoryRequestModel;
import org.example.springshop.model.dto.responsemodel.CategoryResponseModel;
import org.example.springshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseModel> listCategory() {
        List<CategoryResponseModel> categoryResponseModels = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            CategoryResponseModel categoryResponseModel = CategoryResponseModel.builder().category(category).build();
            categoryResponseModels.add(categoryResponseModel);
        });
        return categoryResponseModels;
    }

    public CategoryResponseModel addCategory(CategoryRequestModel categoryRequestModel) {
        Category category = Category.categoryBuilder().categoryRequestModel(categoryRequestModel).build();
        categoryRepository.save(category);
        return CategoryResponseModel.builder().category(category).build();
    }

    public CategoryResponseModel editCategory(Long id, CategoryRequestModel categoryRequestModel) {
        Category updatCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("category not found"));
        updatCategory.setName(categoryRequestModel.getName());
        updatCategory.setDescription(categoryRequestModel.getDescription());
        return CategoryResponseModel.builder().category(updatCategory).build();
    }

    public CategoryResponseModel showProduct(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return CategoryResponseModel.builder().category(category).build();
    }

    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(category);
        return "category deleted";
    }

}
