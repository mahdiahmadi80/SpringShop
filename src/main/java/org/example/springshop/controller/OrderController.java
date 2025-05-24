package org.example.springshop.controller;

import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.OrderRequestModel;
import org.example.springshop.repository.OrderRepository;
import org.example.springshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Order> orderList() {
        return orderService.orderList();
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Order orderAdd(@RequestBody OrderRequestModel orderRequestModel) {
        return orderService.orderAdd(orderRequestModel);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public Order orderEdit(@PathVariable Long id, @RequestBody OrderRequestModel orderRequestModel) {
        return orderService.orderEdit(id, orderRequestModel);
    }

    @RequestMapping(value = "/delete/{id}")
    public void orderDelete(@PathVariable Long id) {
        orderService.orderDelete(id);
    }
}
