package org.example.springshop.repository;

import org.example.springshop.model.OrderItems;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
//    List<OrderItems> findByOrderItemList(List<OrderItemsRequestModel> orderItems);
}
