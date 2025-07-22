package org.example.springshop.controller;

import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
import org.example.springshop.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/checkout/{cartid}", method = RequestMethod.PATCH)
    public OrderResponseModel checkOutCart(@PathVariable Long cartid) {
        return orderService.checkOutCart(cartid);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderResponseModel> listOrder() {
        return orderService.listOrder();
    }

    @DeleteMapping(value = "cancel/{id}")
    public String cancelOrder(@PathVariable Long id) {
        return orderService.cancelingOrder(id);
    }

    @RequestMapping(value = "/payment/{id}", method = RequestMethod.POST)
    public String paymentOrder(@PathVariable Long id) {
        return orderService.paymentOrder(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String orderDelete(@PathVariable Long id) {
        return orderService.orderDelete(id);
    }

    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    public OrderResponseModel SearchOrderById(@PathVariable Long id) {
        return orderService.searchById(id);
    }
}
