package org.example.springshop.repository;

import org.example.springshop.model.Order;
import org.example.springshop.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    @Query(value = "select * from TBL_ORDERITEMS where ORDER_ID like %:orderid% ", nativeQuery = true)
    List<OrderItems> findByOrderListId(Long orderid);

    OrderItems order(Order order);
}
