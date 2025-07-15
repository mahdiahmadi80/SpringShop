package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderItemsResponseModel;
import org.example.springshop.repository.OrderItemsRepository;
import org.example.springshop.service.OrderItemsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orderItems")
public class OrderItemsController {
    private final OrderItemsService orderItemsService;

    public OrderItemsController(OrderItemsService orderItemsService, OrderItemsRepository orderItemsRepository) {
        this.orderItemsService = orderItemsService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderItemsResponseModel> listOrderItems() {
        return orderItemsService.listOrderItems();
    }

    //    @RequestMapping(value = "/off/{id}", method = RequestMethod.POST)
//    public OrderResponseModel offItem(@PathVariable Long id) {
//        return OrderItemsService.offItem(id);
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public OrderItemsResponseModel addOrderItems(@RequestBody OrderItemsRequestModel orderItemsRequestModel) {
        return orderItemsService.addOrderItems(orderItemsRequestModel);
    }
//    @RequestMapping(value = "/edit/{id}")
//    public OrderItemsResponseModel editOrderItem(@PathVariable Long id, @RequestBody OrderItemsRequestModel orderItemsRequestModel) {
//        return orderItemsRepository.editOrderItem(id, orderItemsRequestModel);
//    }



}
