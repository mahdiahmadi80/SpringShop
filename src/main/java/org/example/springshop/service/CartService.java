package org.example.springshop.service;

import org.example.springshop.model.Cart;
import org.example.springshop.model.dto.responsemodel.CartResponseModel;
import org.example.springshop.repository.CartRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<CartResponseModel> listCart() {
        List<CartResponseModel> cartResponseModels = new ArrayList<>();
        cartRepository.findAll().forEach(cart -> {
            CartResponseModel cartResponseModel = CartResponseModel.builder().cart(cart).build();
            cartResponseModels.add(cartResponseModel);
        });
        return cartResponseModels;
    }

    public CartResponseModel addCart() {

    }

    public String deleteCart(Long id) {
        cartRepository.deleteById(id);
        return "Cart is deleted";
    }

    public String clearCart(Long id) {
        Cart updatecart = cartRepository.findById(id).orElseThrow();
        updatecart.get;
    }

//    public CartResponseModel addCart(CartRequestModel cartRequestModel) {
//
//
//    }


//    public CartResponseModel addCart(CartItemsRequestModel  cartItemsRequestModel) {
//        User user = userRepository.findById(cartItemsRequestModel.).orElseThrow();
//
//
//    }

}
