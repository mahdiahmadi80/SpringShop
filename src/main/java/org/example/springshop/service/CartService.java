package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.cartException.CartNotFoundException;
import org.example.springshop.exception.ExceptionMessage;
import org.example.springshop.exception.itemNotFoundException.ItemNotFoundException;
import org.example.springshop.exception.productException.ProductException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.Cart;
import org.example.springshop.model.CartItems;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.CartItemsRequestModel;
import org.example.springshop.model.dto.requestmodel.CartRequestModel;
import org.example.springshop.model.dto.responsemodel.CartResponseModel;
import org.example.springshop.repository.CartItemsRepository;
import org.example.springshop.repository.CartRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    public List<CartResponseModel> listCart() {
        List<CartResponseModel> cartResponseModels = new ArrayList<>();
        cartRepository.findAll().forEach(cart -> {
            CartResponseModel cartResponseModel = CartResponseModel.builder().cart(cart).build();
            cartResponseModels.add(cartResponseModel);
        });
        return cartResponseModels;
    }

    public void checkQuantity(Product product, Long quantity) {
        if (quantity > product.getInventory()) {
            throw new ProductException(ExceptionMessage.productNotEnough);
        }
    }

    @Transactional
    public CartResponseModel addCart(CartRequestModel cartRequestModel) {
        User user = userRepository.findById(cartRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException(ExceptionMessage.userNotFound));
        Cart cart = Cart.cartBuilder().user(user).build();

        return addItem(cartRequestModel, cart);
    }

    public CartResponseModel addCartItem(CartRequestModel cartRequestModel) {
        Cart cart = cartRepository.findCartById(cartRequestModel.getId()).orElseThrow(() -> new CartNotFoundException(ExceptionMessage.cartNotFound));
        return addItem(cartRequestModel, cart);
    }

    public CartResponseModel addItem(CartRequestModel cartRequestModel, Cart cart) {
        for (CartItemsRequestModel cartItemsRequestModel : cartRequestModel.getListItem()) {
            Product product = productRepository.findById(cartItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException(ExceptionMessage.productNotFound));
            CartItems cartItems = CartItems.cartItemsBuilder().product(product).cart(cart).build();
            checkQuantity(product, cartItemsRequestModel.getQuantity());
            cartItems.setQuantity(cartItemsRequestModel.getQuantity());
            cartRepository.save(cart);
            cartItemsRepository.save(cartItems);
        }
        return CartResponseModel.builder().cart(cart).build();
    }

    public ResponseEntity<String> clearCartItem(Long id) {
        Cart cart = cartRepository.findCartById(id).orElseThrow(() -> new CartNotFoundException(ExceptionMessage.cartNotFound));
        List<CartItems> cartItems = cartItemsRepository.findByCart(cart);
        cartItemsRepository.deleteAll(cartItems);
        return new ResponseEntity<>(ExceptionMessage.clearSuccessful,HttpStatus.OK);
    }

    public ResponseEntity<String> deleteItem(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ExceptionMessage.itemNotFound));
        cartItemsRepository.delete(cartItems);
        return ResponseEntity.ok(ExceptionMessage.deleteSuccessful);
    }

    public ResponseEntity<String> deleteCart(Long id) {
        Cart cart = cartRepository.findCartById(id).orElseThrow(() -> new CartNotFoundException(ExceptionMessage.cartNotFound));
        List<CartItems> cartItemsList = cartItemsRepository.findByCart(cart);
        cartItemsRepository.deleteAll(cartItemsList);
        cartRepository.deleteById(id);
        return ResponseEntity.ok(ExceptionMessage.deleteSuccessful);
    }
}
