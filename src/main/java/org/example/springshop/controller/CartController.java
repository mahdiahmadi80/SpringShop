package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.CartRequestModel;
import org.example.springshop.model.dto.responsemodel.CartResponseModel;
import org.example.springshop.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CartResponseModel> listCart() {
        return cartService.listCart();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CartResponseModel addCart(@RequestBody CartRequestModel cartRequestModel) {
        return cartService.addCart(cartRequestModel);
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public CartResponseModel editCart(@PathVariable Long id, @RequestBody CartRequestModel cartResponseModel) {
        return cartService.editCart(id, cartResponseModel);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteCart(@PathVariable Long id) {
        return cartService.deleteCart(id);
    }

    @RequestMapping(value = "/clear/{id}", method = RequestMethod.POST)
    public String clearCartItem(@PathVariable Long id) {
        return cartService.clearCartItem(id);
    }
}
