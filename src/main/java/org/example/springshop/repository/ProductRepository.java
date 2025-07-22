package org.example.springshop.repository;

import org.example.springshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByName(String name);

    @Query(value = "select * from tbl_product where PRODUCT_PRICE >= :price", nativeQuery = true)
    List<Product> searchByProductPrice(Long price);

    List<Product> searchByPriceBetween(Long minPrice, Long maxPrice);

    List<Product> findByCategoryId(Long category);

}
