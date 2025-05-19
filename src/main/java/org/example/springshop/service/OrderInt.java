package org.example.springshop.service;

import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.OrderRequestModel;

import java.util.List;

public interface OrderInt {
    List<Order> orderList();

    Order orderAdd(OrderRequestModel orderrequestModel);

    Order orderEdit(Long id, OrderRequestModel orderRequestModel);

    void orderDelete(Long id);

    User userSearch(Long id);


    Order searchOrder(OrderRequestModel orderRequestModel);
}
