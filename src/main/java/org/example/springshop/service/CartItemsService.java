package org.example.springshop.service;

import org.example.springshop.exception.CartNotFoundException;
import org.example.springshop.model.CartItems;
import org.example.springshop.model.Product;
import org.example.springshop.model.dto.requestmodel.CartItemsRequestModel;
import org.example.springshop.model.dto.responsemodel.CartItemsResponseModel;
import org.example.springshop.repository.CartItemsRepository;
import org.example.springshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemsService {
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository, ProductRepository productRepository) {
        this.cartItemsRepository = cartItemsRepository;
        this.productRepository = productRepository;
    }

    public List<CartItemsResponseModel> listCartItems() {
        List<CartItemsResponseModel> cartItemsResponseModels = new ArrayList<>();
        cartItemsRepository.findAll().forEach(cartItems -> {
            CartItemsResponseModel cartItemsResponseModel = CartItemsResponseModel.builder().cartItems(cartItems).build();
            cartItemsResponseModels.add(cartItemsResponseModel);
        });
        return cartItemsResponseModels;
    }

    public CartItemsResponseModel addCartItems(CartItemsRequestModel cartItemsRequestModel) {
        Product product = productRepository.findById(cartItemsRequestModel.getProductId()).orElseThrow();
        CartItems cartItems = CartItems.cartItemsBuilder().cartItemsRequestModel(cartItemsRequestModel).product(product).build();
        cartItemsRepository.save(cartItems);
        return CartItemsResponseModel.builder().cartItems(cartItems).build();
    }

    public String deleteCartItem(Long id) {
       cartItemsRepository.findById(id).orElseThrow(()->new CartNotFoundException("cart not found"));
        cartItemsRepository.deleteById(id);
        return "cart deleted";
    }

}
