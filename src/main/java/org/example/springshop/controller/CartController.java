package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.CartRequestModel;
import org.example.springshop.model.dto.responsemodel.CartResponseModel;
import org.example.springshop.service.CartService;
import org.example.springshop.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "Cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<CartResponseModel> listCart(){
        return cartService.listCart();
    }

//    @RequestMapping(value = "/search/id/{id}")
//    public

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public CartResponseModel addCart(@RequestBody CartRequestModel cartRequestModel) {
//        return cartService.addCart(cartRequestModel);
//    }


}
