package org.example.springshop.service;

import org.example.springshop.model.Order;
import org.example.springshop.model.OrderItems;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderItemsResponseModel;
import org.example.springshop.repository.OrderItemsRepository;
import org.example.springshop.repository.OrderRepository;
import org.example.springshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderItemsService(OrderItemsRepository orderItemsRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderItemsResponseModel> listOrderItems() {
        List<OrderItemsResponseModel> orderItemsResponseModels = new ArrayList<>();
        orderItemsRepository.findAll().forEach(orderItems -> {
            OrderItemsResponseModel orderItemsResponseModel = OrderItemsResponseModel.builder().orderItems(orderItems).product(orderItems.getProduct()).build();
            orderItemsResponseModels.add(orderItemsResponseModel);
        });
        return orderItemsResponseModels;
    }

    public OrderItemsResponseModel addOrderItems(OrderItemsRequestModel orderItemsRequestModel) {
        Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow();
        Order order = orderRepository.findById(orderItemsRequestModel.getOrderId()).orElseThrow();
        OrderItems orderItems = OrderItems.orderItemsBuilder().orderItemsRequestModel(orderItemsRequestModel).order(order).product(product).build();

        orderItemsRepository.save(orderItems);
        return OrderItemsResponseModel.builder().orderItems(orderItems).build();
    }
}
