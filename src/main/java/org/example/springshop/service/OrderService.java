package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.orderException.NotEnoughBalanceException;
import org.example.springshop.exception.orderException.OrderAddFailException;
import org.example.springshop.exception.orderException.OrdetNotFoundException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.exception.walletException.BalanceException;
import org.example.springshop.model.*;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
import org.example.springshop.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WalletRepository walletRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, WalletService walletService, WalletRepository walletRepository, CartRepository cartRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.walletRepository = walletRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    public List<OrderResponseModel> listOrder() {
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(order).build();
            orderResponseModels.add(orderResponseModel);
        });
        return orderResponseModels;
    }

    @Transactional
    public OrderResponseModel addOrder(OrderRequestModel orderRequestModel) {
        User user = userRepository.findById(orderRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        List<OrderItems> orderItemsList = new ArrayList<>();
        Order order = Order.orderBuilder().user(user).orderItems(orderItemsList).build();

        Long totalAmount = 0L;
        for (OrderItemsRequestModel orderItemsRequestModel : orderRequestModel.getOrderItems()) {
            Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
//            Long quantity = orderItemsRequestModel.getQuantity();
//            checkQuantity(product, quantity);
//            totalAmount += totalPrice(product, quantity);
            OrderItems orderItems = OrderItems.orderItemsBuilder().product(product).orderItemsRequestModel(orderItemsRequestModel).build();
//            updateQuantity(product, quantity);
            orderItemsList.add(orderItems);
        }

//        checkWallet(user, totalAmount);
//        deduceBalance(user, totalAmount);
//        order.setUser(user);
//        order.setOrderItems(orderItemsList);
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);
        return OrderResponseModel.builder().order(order).build();
    }


    public Long totalPrice(Product product, Long quantity) {
        return product.getPrice() * quantity;
    }

    public Boolean checkQuantity(Product product, Long quantity) {
        if (quantity > product.getInventory()) {
            throw new ProductNotFoundException("your count over than quantity");
        }
        return false;
    }


    public void checkWallet(User user, Long totalAmount) {
        Wallet wallet = walletRepository.findById(user.getId()).orElseThrow();
        Long walletBalance = wallet.getBalance() - totalAmount;
        if (walletBalance < 0) {
            throw new NotEnoughBalanceException("your balance not enough");
        }
    }

    public void updateQuantity(Product product, Long count) {
        product.setInventory(product.getInventory() - count);
        if (product.getInventory() < 0) {
            throw new OrderAddFailException("product not enough");
        }
        productRepository.save(product);
    }


    public String deduceBalance(User user, Long totalAmount) {
        Wallet wallet = walletRepository.findById(user.getId()).orElseThrow();

        wallet.setBalance(wallet.getBalance() - totalAmount);
        if (wallet.getBalance() < 0) {
            throw new BalanceException("your balance not enough");
        }
        userRepository.save(user);
        return "payment is ok";
    }

    @Transactional
    public OrderResponseModel editOrder(Long id, OrderRequestModel orderRequestModel) {
        Order updateOrder = orderRepository.findById(id).orElseThrow(() -> new OrdetNotFoundException("order not found"));
        User updateUser = userRepository.findById(orderRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Wallet wallet = walletRepository.findById(updateUser.getId()).orElseThrow();

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

            OrderItems orderItems = OrderItems.orderItemsBuilder().order(updateOrder).product(product).orderItemsRequestModel(orderItemsRequestModel).build();
            orderItemsList.add(orderItems);
        }

        checkWallet(updateUser, totalAmount);
        wallet.setBalance(wallet.getBalance() - totalAmount);
        userRepository.save(updateUser);
        updateOrder.setUser(updateUser);
        updateOrder.setOrderItems(orderItemsList);
        updateOrder.setTotalAmount(totalAmount);
        orderRepository.save(updateOrder);
        return OrderResponseModel.builder().order(updateOrder).build();
    }

    public String orderDelete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        backBalance(order, order.getTotalAmount());
        orderRepository.deleteById(id);
        return "your order deleted";
    }

    public String backBalance(Order order, Long totalPrice) {
        User user = userRepository.findById(order.getUser().getId()).orElseThrow();
        Wallet wallet = walletRepository.findById(user.getId()).orElseThrow();
        Long balance = wallet.getBalance() + totalPrice;
        wallet.setBalance(balance);
        //        Long balance = order.getUser().getWallet().getBalance() + totalPrice;
//        order.getUser().getWallet().setBalance(balance);
        return "your money deposit in your wallet";
    }

    public Long backProduct(Product product, OrderItems orderItems) {
        return product.getInventory() + orderItems.getQuantity();
    }

    public OrderResponseModel searchById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return OrderResponseModel.builder().order(order).build();
    }

//    @Transactional
//    public OrderResponseModel checkOutCart(Long cartId) {
//        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("cart not found"));
//        User user = userRepository.findById(cart.getUser().getId()).orElseThrow(() -> new UserNotFoundException("user not found"));
//        List<CartItems> cartItemsList = cart.getCartItems();
//        List<OrderItems> orderItemsList = new ArrayList<>();
//
//        Long totalAmount = 0L;
//        for (CartItems cartItems : cartItemsList) {
//            Product product = cartItems.getProduct();
//            Long quantity = product.getInventory();
//            checkQuantity(product, quantity);
//            totalAmount += totalPrice(product, quantity);
//            OrderItems orderItems = OrderItems.orderItemsBuilder().quantity(quantity).amount(totalAmount).product(product).build();
//            orderItemsList.add(orderItems);
//        }
//        Order order = Order.orderBuilder().user(user).totalAmount(totalAmount).orderItems(orderItemsList).build();
//        for (OrderItems orderItems : orderItemsList) {
//            orderItems.setOrder(order);
//        }
//        checkWallet(user, totalAmount);
//        deduceBalance(user, totalAmount);
//        orderRepository.save(order);
//        cartService.clearCartItem(cartId);
//        cartRepository.save(cart);
//        return OrderResponseModel.builder().order(order).build();
//    }

}
