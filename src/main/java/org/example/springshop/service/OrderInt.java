package org.example.springshop.service;

import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.OrderRequestModel;

import java.util.List;

public interface OrderInt {
    List<Order> orderList();

    Order orderAdd(OrderRequestModel orderRequestModel);


    Order orderEdit(Long id, OrderRequestModel orderRequestModel);

    User userSearch(Long id);

    void orderDelete(Long id);
}
