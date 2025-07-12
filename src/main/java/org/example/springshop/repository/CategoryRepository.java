package org.example.springshop.repository;

import org.example.springshop.model.Category;
import org.example.springshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
