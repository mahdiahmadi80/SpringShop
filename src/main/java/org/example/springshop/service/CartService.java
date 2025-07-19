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
    private final ProductService productService;
    private final CartItemsService cartItemsService;
    private final CartItemsRepository cartItemsRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, ProductService productService, CartItemsService cartItemsService, CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.cartItemsService = cartItemsService;
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

    public CartResponseModel addCart(CartRequestModel cartRequestModel) {
        User user = userRepository.findById(cartRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        List<CartItems> cartItemList = new ArrayList<>();
        Cart cart = Cart.cartBuilder().user(user).cartItems(cartItemList).build();
        for (CartItemsRequestModel cartItemsRequestModel : cartRequestModel.getCartItems()) {
            Product product = productRepository.findById(cartItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
            Long quantity = cartItemsRequestModel.getQuantity();
            checkQuantity(product, quantity);
            CartItems cartItems = CartItems.cartItemsBuilder().cart(cart).product(product).cartItemsRequestModel(cartItemsRequestModel).build();
            cartItemList.add(cartItems);
        }
        cartRepository.save(cart);
        return CartResponseModel.builder().cart(cart).build();
    }

    public Long backProduct(Product product, CartItems cartItems) {
        return product.getInventory() + cartItems.getQuantity();
    }


    public CartResponseModel editCart(Long id, CartRequestModel cartRequestModel) {
        Cart updatecart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("cart Not found"));
        updatecart.setUser(updatecart.getUser());
        for (CartItems items : updatecart.getCartItems()) {
            Product product = items.getProduct();

            productRepository.save(product);
        }
        updatecart.getCartItems().clear();
        List<CartItems> cartItemsList = new ArrayList<>();
        for (CartItemsRequestModel cartItemsRequestModel : cartRequestModel.getCartItems()) {
            Product product = productRepository.findById(cartItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
            Long quantity = cartItemsRequestModel.getQuantity();
            productService.checkQuantity(product, quantity);
            product.setInventory(product.getInventory() - quantity);
            productRepository.save(product);
            CartItems cartItems = CartItems.cartItemsBuilder().cart(updatecart).cartItemsRequestModel(cartItemsRequestModel).product(product).build();
            cartItemsList.add(cartItems);
        }
        updatecart.setCartItems(cartItemsList);
        cartRepository.save(updatecart);
        return CartResponseModel.builder().cart(updatecart).build();
    }


    public String deleteCart(Long id) {
        cartRepository.findCartById(id).orElseThrow(() -> new CartNotFoundException("cart not found"));
        cartRepository.deleteById(id);
        return "Cart deleted";
    }

    public String clearCartItem(Long id) {
        Cart updatecart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("cart not found"));

        List<CartItems> cartItemsList = updatecart.getCartItems();
        for (CartItems cartItems : cartItemsList) {
            cartItemsService.deleteCartItem(cartItems.getId());
        }
        cartRepository.save(updatecart);
        return "cart cleared";
    }
}
