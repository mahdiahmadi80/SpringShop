package org.example.springshop.repository;

import org.example.springshop.model.Order;
import org.example.springshop.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrder(Order order);

    Optional<OrderItems> deleteAllByOrder(Order order);
}
