package org.example.springshop.service;

import org.example.springshop.exception.CartNotFoundException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.Cart;
import org.example.springshop.model.CartItems;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.CartItemsRequestModel;
import org.example.springshop.model.dto.requestmodel.CartRequestModel;
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

    public CartResponseModel addCart(CartRequestModel cartRequestModel) {
        User user = userRepository.findById(cartRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        List<CartItems> cartItemList = new ArrayList<>();

        Cart cart = Cart.cartBuilder().cartItems(cartItemList).user(user).build();

        for (CartItemsRequestModel cartItemsRequestModel : cartRequestModel.getCartItems()) {
            Product product = productRepository.findById(cartItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
            CartItems cartItems = CartItems.cartItemsBuilder().cart(cart).product(product).cartItemsRequestModel(cartItemsRequestModel).build();
            cartItemList.add(cartItems);
        }
        cartRepository.save(cart);
        return CartResponseModel.builder().cart(cart).build();
    }

    public String deleteCart(Long id) {
        cartRepository.deleteById(id);
        return "Cart deleted";
    }

    public String clearCartItem(Long id) {
        Cart updatecart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("cart not found"));
        updatecart.getCartItems().clear();
        cartRepository.save(updatecart);
        return "cart cleared";
    }
}
