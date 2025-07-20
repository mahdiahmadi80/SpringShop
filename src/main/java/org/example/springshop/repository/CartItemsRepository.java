package org.example.springshop.repository;

import org.example.springshop.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    @Query(value = "select * from TBL_CARTITEMS where CART_ID like %:cartid%", nativeQuery = true)
    Optional<CartItems> findByCartId(Long cartid);

    @Query(value = "select * from TBL_CARTITEMS where CART_ID like %:cartlist%", nativeQuery = true)
    List<CartItems> findByCartListId(Long cartlist);

    @Modifying
    @Query(value = "delete * from TBL_CARTITEM where CART_ID LIKE %:cartid%", nativeQuery = true)
    void deleteByCartId(Long cartid);
}
