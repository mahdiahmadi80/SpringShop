package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.CartNotFoundException;
import org.example.springshop.exception.ItemNotFoundException;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, ProductService productService, CartItemsRepository cartItemsRepository) {
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
            throw new ProductNotFoundException("your count is over than inventory");
        }
    }

    @Transactional
    public CartResponseModel addCart(CartRequestModel cartRequestModel) {
        User user = userRepository.findById(cartRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Cart cart = Cart.cartBuilder().user(user).build();

        for (CartItemsRequestModel cartItemsRequestModel : cartRequestModel.getListItem()) {
            Product product = productRepository.findById(cartItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
            CartItems cartItems = CartItems.cartItemsBuilder().product(product).cart(cart).build();
            checkQuantity(product, cartItemsRequestModel.getQuantity());
            cartItems.setQuantity(cartItemsRequestModel.getQuantity());
            cartRepository.save(cart);
            cartItemsRepository.save(cartItems);
        }
        return CartResponseModel.builder().cart(cart).build();
    }

    public Long backProduct(Product product, CartItems cartItems) {
        return product.getInventory() + cartItems.getQuantity();
    }
    public String clearCartItem(Long id) {
        Cart cart = cartRepository.findCartById(id).orElseThrow(() -> new CartNotFoundException("cart not found"));
        List<CartItems> cartItems = cartItemsRepository.findByCartListId(cart.getId());
        cartItemsRepository.deleteAll(cartItems);
        return "cart is clear";
    }

    public String deleteItem(Long id) {
        Cart cart = cartRepository.findCartById(id).orElseThrow(() -> new CartNotFoundException("cart not found"));
        CartItems cartItems = cartItemsRepository.findByCartId(cart.getId()).orElseThrow(() -> new ItemNotFoundException("item not found"));
        cartItemsRepository.delete(cartItems);
        return "item deleted";
    }

    public String deleteCart(Long id) {
        Cart cart = cartRepository.findCartById(id).orElseThrow(() -> new CartNotFoundException("cart not found"));
        List<CartItems> cartItemsList = cartItemsRepository.findByCartListId(cart.getId());
        cartItemsRepository.deleteAll(cartItemsList);
        cartRepository.deleteById(id);
        return "Cart deleted";
    }
}
