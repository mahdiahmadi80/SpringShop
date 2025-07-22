package org.example.springshop.service;


import org.example.springshop.model.dto.responsemodel.OrderItemsResponseModel;
import org.example.springshop.repository.OrderItemsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;

    public OrderItemsService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public List<OrderItemsResponseModel> listOrderItems() {
        List<OrderItemsResponseModel> orderItemsResponseModels = new ArrayList<>();
        orderItemsRepository.findAll().forEach(orderItems -> {
            OrderItemsResponseModel orderItemsResponseModel = OrderItemsResponseModel.builder().orderItems(orderItems).product(orderItems.getProduct()).build();
            orderItemsResponseModels.add(orderItemsResponseModel);
        });
        return orderItemsResponseModels;
    }


}
