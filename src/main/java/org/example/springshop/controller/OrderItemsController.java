package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderItemsResponseModel;
import org.example.springshop.service.OrderItemsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orderItems")
public class OrderItemsController {
    private final OrderItemsService orderItemsService;

    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderItemsResponseModel> listOrderItems() {
        return orderItemsService.listOrderItems();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public OrderItemsResponseModel addOrderItems(@RequestBody OrderItemsRequestModel orderItemsRequestModel) {
        return orderItemsService.addOrderItems(orderItemsRequestModel);
    }
}
