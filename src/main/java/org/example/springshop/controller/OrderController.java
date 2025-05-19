package org.example.springshop.controller;

import org.example.springshop.model.Order;
import org.example.springshop.model.dto.OrderRequestModel;
import org.example.springshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Order orderAdd(@RequestBody OrderRequestModel orderRequestModel){
        return orderService.orderAdd(orderRequestModel);

    }
}
