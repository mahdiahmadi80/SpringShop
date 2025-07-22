package org.example.springshop.service;

import org.example.springshop.model.dto.responsemodel.CartItemsResponseModel;
import org.example.springshop.repository.CartItemsRepository;
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
}
