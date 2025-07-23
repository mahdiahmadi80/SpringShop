package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.Address.AddressException;
import org.example.springshop.exception.ExceptionMessage;
import org.example.springshop.exception.cartException.CartNotFoundException;
import org.example.springshop.exception.orderException.OrderNotFoundException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.*;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
import org.example.springshop.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final CartRepository cartRepository;
    private final WalletService walletService;
    private final ProductService productService;
    private final CartItemsRepository cartItemsRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, WalletService walletService, WalletRepository walletRepository, CartRepository cartRepository, ProductService productService, CartItemsRepository cartItemsRepository, OrderItemsRepository orderItemsRepository, AddressRepository addressRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.cartRepository = cartRepository;
        this.walletService = walletService;
        this.productService = productService;
        this.cartItemsRepository = cartItemsRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }

    public List<OrderResponseModel> listOrder() {
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(order).build();
            orderResponseModels.add(orderResponseModel);
        });
        return orderResponseModels;
    }

    public String orderDelete(Long id) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new OrderNotFoundException(ExceptionMessage.orderNotFound));
        orderItemsRepository.findByOrder(order).forEach(orderItems -> {
            orderItemsRepository.deleteAll();
        });
        orderRepository.deleteById(id);
        return ExceptionMessage.deleteSuccessful;
    }

    public void backBalance(Order order, Long totalPrice) {
        User user = userRepository.findById(order.getUser().getId()).orElseThrow();
        Wallet wallet = walletRepository.findWalletByUserId(user).orElseThrow();
        Long balance = wallet.getBalance() + totalPrice;
        wallet.setBalance(balance);
    }

    public OrderResponseModel searchById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return OrderResponseModel.builder().order(order).build();
    }

    @Transactional
    public String paymentOrder(Long id) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new OrderNotFoundException(ExceptionMessage.orderNotFound));
        if (!order.isPay()) {
            Long totalAmount = order.getTotalAmount();
            walletService.deduceWallet(order, totalAmount);
            order.setPaymentAt(LocalDateTime.now());
            order.setPay(true);
            orderRepository.save(order);
        }
        return ExceptionMessage.paySuccessful;
    }
@Transactional
    public String cancelingOrder(Long id) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new OrderNotFoundException(ExceptionMessage.orderNotFound));
        if (order.isPay()) {
            orderItemsRepository.findByOrder(order).forEach(orderItems -> {
                Product product = productRepository.findById(orderItems.getProduct().getId()).orElseThrow(() -> new ProductNotFoundException(ExceptionMessage.productNotFound));
                product.setInventory(product.backProduct(product, orderItems));
                productRepository.save(product);
                orderItemsRepository.deleteAllByOrder(order);
            });
            backBalance(order, order.getTotalAmount());
            orderRepository.deleteById(id);
            return ExceptionMessage.cancelOrderSuccessful;
        }
        return ExceptionMessage.orderNotPaid;
    }

    @Transactional
    public OrderResponseModel checkOutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(ExceptionMessage.cartNotFound));

        List<CartItems> cartItemsList = cartItemsRepository.findByCart(cart);


        User user = userRepository.findById(cart.getUser().getId()).orElseThrow(() -> new UserNotFoundException(ExceptionMessage.userNotFound));
        Address address = addressRepository.findByUserId(user).orElseThrow(() -> new AddressException(ExceptionMessage.addressNotFound));


        Order order = Order.orderBuilder().user(user).address(address).totalAmount(0L).build();
//            orderRepository.save(order);
        Long totalAmount = 0L;
        for (CartItems cartItems : cartItemsList) {
            Product product = cartItems.getProduct();
            Long quantity = cartItems.getQuantity();
            productService.checkQuantity(product, quantity);
            Long itemPrice = productService.itemPrice(product, quantity);
            totalAmount += itemPrice;
            productService.updateQuantity(product, quantity);
            OrderItems orderItems = OrderItems.orderItemsBuilder().amount(itemPrice).product(product).quantity(quantity).order(order).build();
//            orderItems.setOrder(order);
            orderRepository.save(order);
            orderItemsRepository.save(orderItems);
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        cartItemsRepository.deleteAll(cartItemsList);
        cartRepository.deleteById(cart.getId());
        return OrderResponseModel.builder().order(order).build();
    }
}
