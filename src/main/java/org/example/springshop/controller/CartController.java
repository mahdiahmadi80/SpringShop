package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.CartRequestModel;
import org.example.springshop.model.dto.responsemodel.CartResponseModel;
import org.example.springshop.service.CartService;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        return cartService.deleteCart(id);
    }

    @DeleteMapping(value = "/clear/{id}")
    public ResponseEntity<String> clearCartItem(@PathVariable Long id) {
        return cartService.clearCartItem(id);
    }

    @RequestMapping(value = "/additem/**", method = RequestMethod.POST)
    public CartResponseModel addCartItem(@RequestBody CartRequestModel cartRequestModel) {
        return cartService.addCartItem(cartRequestModel);
    }

    @DeleteMapping(value = "delete/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        return cartService.deleteItem(id);
    }
}
