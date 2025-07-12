package org.example.springshop.repository;

import org.example.springshop.model.Order;
import org.example.springshop.model.OrderItems;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
