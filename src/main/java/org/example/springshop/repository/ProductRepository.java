package org.example.springshop.repository;

import org.example.springshop.model.Comment;
import org.example.springshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from tbl_product where product_name like %:name%", nativeQuery = true)
    List<Product> searchByProductName(String name);

    @Query(value = "select * from tbl_product where PRODUCT_PRICE >= :price", nativeQuery = true)
    List<Product> searchByProductPrice(Long price);

    @Query(value = "select * from tbl_product where product_price between :minPrice and :maxPrice", nativeQuery = true)
    List<Product> searchByPriceBetween(Long minPrice, Long maxPrice);

    @Query(value ="select * from TBL_COMMENT where PRODUCT_ID like %:productId% " ,nativeQuery = true)
    List<Comment> commentOfProductById(Long productId);



    List<Product> findByCategoryId(Long category);

}
