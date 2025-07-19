package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.CartNotFoundException;
import org.example.springshop.exception.orderException.OrderAddFailException;
import org.example.springshop.exception.orderException.OrderNotFoundException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.*;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
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
    private final ProductRepository productRepository;
    private final WalletRepository walletRepository;
    private final CartRepository cartRepository;
    private final WalletService walletService;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, WalletService walletService, WalletRepository walletRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.walletRepository = walletRepository;
        this.cartRepository = cartRepository;
        this.walletService = walletService;
    }

    public List<OrderResponseModel> listOrder() {
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(order).build();
            orderResponseModels.add(orderResponseModel);
        });
        return orderResponseModels;
    }

//    @Transactional
//    public OrderResponseModel addOrder(OrderRequestModel orderRequestModel) {
//        User user = userRepository.findById(orderRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
//        List<OrderItems> orderItemsList = new ArrayList<>();
//        Order order = Order.orderBuilder().user(user).orderItems(orderItemsList).build();
//
//        Long totalAmount = 0L;
//        OrderItems orderItems = null;
//        for (OrderItemsRequestModel orderItemsRequestModel : orderRequestModel.getOrderItems()) {
//            Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
//            Long quantity = orderItemsRequestModel.getQuantity();
//            checkQuantity(product, quantity);
//            totalAmount += totalPrice(product, quantity);
//            orderItems = OrderItems.orderItemsBuilder().product(product).orderItemsRequestModel(orderItemsRequestModel).amount(totalAmount).order(order).build();
//            updateQuantity(product, quantity);
//            System.out.println("Product ID: " + orderItems.getProduct().getId());
//            orderItemsRepository.save(orderItems);
//            orderItemsList.add(orderItems);
//        }
//        checkWallet(user, totalAmount);
//        deduceBalance(user, totalAmount);
//        order.setUser(user);
//        order.setOrderItems(orderItemsList);
//        order.setTotalAmount(totalAmount);
//
//        orderRepository.save(order);
//        return OrderResponseModel.builder().order(order).build();
//    }

    public Long itemPrice(Product product, Long quantity) {
        return product.getPrice() * quantity;
    }

    public void checkQuantity(Product product, Long quantity) {
        if (quantity > product.getInventory()) {
            throw new ProductNotFoundException("your count is over than inventory");
        }
    }

    public void updateQuantity(Product product, Long count) {
        product.setInventory(product.getInventory() - count);
        if (product.getInventory() < 0) {
            throw new OrderAddFailException("product not enough");
        }
        productRepository.save(product);
    }

    @Transactional
    public OrderResponseModel editOrder(Long id, OrderRequestModel orderRequestModel) {
        Order updateOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("order not found"));
        User updateUser = userRepository.findById(orderRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Wallet wallet = walletRepository.findWalletByUserId(updateUser.getId()).orElseThrow();
        for (OrderItems items : updateOrder.getOrderItems()) {
            Product product = items.getProduct();
            product.setInventory(backProduct(product, items));
            productRepository.save(product);
            wallet.setBalance(wallet.getBalance() + (product.getPrice() * items.getQuantity()));
        }

        updateOrder.getOrderItems().clear();
        userRepository.save(updateUser);

        List<OrderItems> orderItemsList = new ArrayList<>();
        Long totalAmount = 0L;
        for (OrderItemsRequestModel orderItemsRequestModel : orderRequestModel.getOrderItems()) {
            Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow();
            Long quantity = orderItemsRequestModel.getQuantity();
            checkQuantity(product, quantity);

            totalAmount += product.getPrice() * quantity;
            product.setInventory(product.getInventory() - quantity);
            productRepository.save(product);

            OrderItems orderItems = OrderItems.orderItemsBuilder().order(updateOrder).product(product).build();
            orderItemsList.add(orderItems);
        }

        walletService.checkWallet(updateUser, totalAmount);
        wallet.setBalance(wallet.getBalance() - totalAmount);
        userRepository.save(updateUser);
        updateOrder.setUser(updateUser);
        updateOrder.setOrderItems(orderItemsList);
        updateOrder.setTotalAmount(totalAmount);
        orderRepository.save(updateOrder);
        return OrderResponseModel.builder().order(updateOrder).build();
    }

    public String orderDelete(Long id) {
        orderRepository.findOrderById(id).orElseThrow(() -> new OrderNotFoundException("order not found"));
        orderRepository.deleteById(id);
        return "your order deleted";
    }

    public void backBalance(Order order, Long totalPrice) {
        User user = userRepository.findById(order.getUser().getId()).orElseThrow();
        Wallet wallet = walletRepository.findWalletByUserId(user.getId()).orElseThrow();
        Long balance = wallet.getBalance() + totalPrice;
        wallet.setBalance(balance);
        //        Long balance = order.getUser().getWallet().getBalance() + totalPrice;
//        order.getUser().getWallet().setBalance(balance);
    }

    public Long backProduct(Product product, OrderItems orderItems) {
        return product.getInventory() + orderItems.getQuantity();
    }

    public OrderResponseModel searchById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return OrderResponseModel.builder().order(order).build();
    }

    @Transactional
    public String paymentOrder(Long id) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new OrderNotFoundException("order not found"));
        Long totalAmount = order.getTotalAmount();
        walletService.deduceWallet(order, totalAmount);
        order.setPaymentAt(LocalDateTime.now());
        order.setPayment(true);
        orderRepository.save(order);
        return "payment is ok ";
    }


    @Transactional
    public OrderResponseModel checkOutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("cart not found"));
        User user = userRepository.findById(cart.getUser().getId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        List<CartItems> cartItemsList = cart.getCartItems();
        List<OrderItems> orderItemsList = new ArrayList<>();
        Long totalAmount = 0L;
        for (CartItems cartItems : cartItemsList) {
            Product product = cartItems.getProduct();
            Long quantity = cartItems.getQuantity();
            checkQuantity(product, quantity);
            Long itemPrice = itemPrice(product, quantity);
            totalAmount += itemPrice;
            updateQuantity(product, quantity);
            OrderItems orderItems = OrderItems.orderItemsBuilder().amount(itemPrice).product(product).quantity(quantity).build();
            orderItemsList.add(orderItems);
        }
        Order order = Order.orderBuilder().user(user).totalAmount(totalAmount).orderItems(orderItemsList).build();
        for (OrderItems orderItems : orderItemsList) {
            orderItems.setOrder(order);
        }
        cartRepository.deleteById(cart.getId());
        orderRepository.save(order);
        return OrderResponseModel.builder().order(order).build();
    }
}
