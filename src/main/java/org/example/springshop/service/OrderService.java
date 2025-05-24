package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.OrderRequestModel;
import org.example.springshop.repository.OrderRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.repository.WalletRepository;
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
    @Autowired
    private WalletRepository walletRepository;


    @Override
    public List<Order> orderList() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Order orderAdd(OrderRequestModel orderRequestModel) {

        User user = userRepository.findById(orderRequestModel.getUserid()).orElseThrow();
        Product product = productRepository.findById(orderRequestModel.getProductid()).orElseThrow();
        Order order = null;
        if (user.getWallet().getBalance() > product.getProductPrice() && product.getProductExist() > 0) {
            order = Order.OrderBuilder().product(product).user(user).build();
        }
        return orderRepository.save(order);
    }

    @Override
    public Order orderEdit(Long id, OrderRequestModel orderRequestModel) {

        Order oldOrder = orderRepository.findById(id).orElseThrow();
        User newUser = userRepository.findById(orderRequestModel.getUserid()).orElseThrow();
        oldOrder.setUser(newUser);
        Product newProduct = productRepository.findById(orderRequestModel.getProductid()).orElseThrow();
        oldOrder.setProduct(newProduct);


        Order newOrder = null;
        if (newUser.getWallet().getBalance() > newProduct.getProductPrice() && newProduct.getProductExist() > 0) {
            newOrder = orderRepository.save(oldOrder);
        }
        return orderRepository.save(newOrder);
    }

    @Override
    public User userSearch(Long id) {
        return orderRepository.findById(id).orElseThrow().getUser();
    }

    @Override
    public void orderDelete(Long id) {
        orderRepository.deleteById(id);
    }
}
