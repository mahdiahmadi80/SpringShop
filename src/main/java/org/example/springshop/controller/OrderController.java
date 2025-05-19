package org.example.springshop.controller;

import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.OrderRequestModel;
import org.example.springshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Order> orderList() {
        return orderService.orderList();
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Order orderAdd(@RequestBody OrderRequestModel orderRequestModel) {
        return orderService.searchOrder(orderRequestModel);

    }
}
