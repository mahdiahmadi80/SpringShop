package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderResponseModel> listOrder() {
        return orderService.listOrder();
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public OrderResponseModel orderAdd(@RequestBody OrderRequestModel orderRequestModel) {
//        return orderService.addOrder(orderRequestModel);
//    }

//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
//    public OrderResponseModel orderEdit(@PathVariable Long id, @RequestBody OrderRequestModel orderRequestModel) {
//        return orderService.editOrder(id, orderRequestModel);
//    }
//    @RequestMapping(value = "/off/{id}", method = RequestMethod.POST)
//    public OrderResponseModel offItem(@PathVariable Long id) {
//        return orderService.offItem(id);
//        }


    @RequestMapping(value = "/payment/{id}",method = RequestMethod.POST)
    public String paymentOrder(@PathVariable Long id){
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

    @RequestMapping(value = "/checkout/{cartid}",method = RequestMethod.POST)
    public OrderResponseModel checkOutCart(@PathVariable Long cartid){
        return orderService.checkOutCart(cartid);
    }


}
