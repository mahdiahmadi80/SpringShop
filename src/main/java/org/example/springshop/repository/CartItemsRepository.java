package org.example.springshop.repository;

import org.example.springshop.model.Cart;
import org.example.springshop.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findByCart(Cart cart);
}
