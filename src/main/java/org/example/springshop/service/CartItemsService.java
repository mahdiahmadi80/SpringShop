package org.example.springshop.service;

import org.example.springshop.exception.CartNotFoundException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.model.Cart;
import org.example.springshop.model.CartItems;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.CartItemsRequestModel;
import org.example.springshop.model.dto.responsemodel.CartItemsResponseModel;
import org.example.springshop.repository.CartItemsRepository;
import org.example.springshop.repository.CartRepository;
import org.example.springshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemsService {
    private final CartItemsRepository cartItemsRepository;


    public CartItemsService(CartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    public List<CartItemsResponseModel> listCartItems() {
        List<CartItemsResponseModel> cartItemsResponseModels = new ArrayList<>();
        cartItemsRepository.findAll().forEach(cartItems -> {
            CartItemsResponseModel cartItemsResponseModel = CartItemsResponseModel.builder().cartItems(cartItems).build();
            cartItemsResponseModels.add(cartItemsResponseModel);
        });
        return cartItemsResponseModels;
    }

    public String deleteCartItem(Long id) {
        cartItemsRepository.findById(id).orElseThrow(() -> new CartNotFoundException("cart not found"));
        cartItemsRepository.deleteById(id);
        return "cart deleted";
    }

}
