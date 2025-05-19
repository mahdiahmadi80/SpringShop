package org.example.springshop.service;

import org.example.springshop.model.Order;
import org.example.springshop.model.dto.OrderRequestModel;
import org.example.springshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderService implements OrderInt {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public List<Order> orderList() {
        return List.of();
    }

    @Override
    public Order orderAdd(OrderRequestModel orderrequestModel) {
        return null;
    }

    @Override
    public Order orderEdit(Long id, OrderRequestModel orderRequestModel) {
        return null;
    }

    @Override
    public void orderDelete() {

    }
}
