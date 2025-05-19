package org.example.springshop.service;

import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.OrderRequestModel;
import org.example.springshop.repository.OrderRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderService implements OrderInt {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


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
    public void orderDelete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public User userSearch(Long id) {
        return orderRepository.findById(id).orElseThrow().getUser();
    }


    @Override
    public Order searchOrder(OrderRequestModel orderRequestModel) {
        User user = userRepository.findById(orderRequestModel.getId()).orElseThrow();
        Product product = productRepository.findById(orderRequestModel.getUserid()).orElseThrow();
        Order order = Order.OrderBuilder().product(product).user(user).build();
        return orderRepository.save(order);
    }
}
