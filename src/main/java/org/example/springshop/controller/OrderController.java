package org.example.springshop.controller;

import org.example.springshop.model.Order;
import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
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
    public List<OrderResponseModel> orderList() {
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

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public void orderDelete(@PathVariable Long id) {
        orderService.orderDelete(id);
    }
}
