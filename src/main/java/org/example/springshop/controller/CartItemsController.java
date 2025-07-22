package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.CartItemsRequestModel;
import org.example.springshop.model.dto.responsemodel.CartItemsResponseModel;
import org.example.springshop.service.CartItemsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cartitems")
public class CartItemsController {
    private final CartItemsService cartItemsService;

    public CartItemsController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

//    @RequestMapping(value = "/off/{id}", method = RequestMethod.POST)
//    public CartItemsResponseModel offItem(@PathVariable Long id) {
//        return cartItemsService.offItem(id);
//    }//TODO

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CartItemsResponseModel> listCartItems() {
        return cartItemsService.listCartItems();
    }


}
