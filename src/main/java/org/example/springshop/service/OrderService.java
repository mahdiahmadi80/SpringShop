package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.Address.AddressException;
import org.example.springshop.exception.CartNotFoundException;
import org.example.springshop.exception.orderException.OrderNotFoundException;
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

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, WalletService walletService, WalletRepository walletRepository, CartRepository cartRepository, ProductService productService, CartItemsRepository cartItemsRepository, OrderItemsRepository orderItemsRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.cartRepository = cartRepository;
        this.walletService = walletService;
        this.productService = productService;
        this.cartItemsRepository = cartItemsRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.addressRepository = addressRepository;
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
//        Order order = Order.orderBuilder().user(user).build();
//
//        Long totalAmount = 0L;
//        for (OrderItemsRequestModel orderItemsRequestModel : orderRequestModel.getOrderItems()) {
//            Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
//            Long quantity = orderItemsRequestModel.getQuantity();
//            totalAmount += productService.itemPrice(product, quantity);
//            productService.checkQuantity(product, quantity);
//            OrderItems orderItems = OrderItems.orderItemsBuilder().product(product).amount(totalAmount).order(order).build();
//            productService.updateQuantity(product, quantity);
//            orderItems.setQuantity(orderItemsRequestModel.getQuantity());
//            walletService.checkWallet(user, totalAmount);
//            walletService.deduceWallet(order, totalAmount);
//            order.setUser(user);
//            order.setTotalAmount(totalAmount);
//            orderRepository.save(order);
//            orderItemsRepository.save(orderItems);
//        }
//
//        return OrderResponseModel.builder().order(order).build();
//    }


    @Transactional
//    public OrderResponseModel editOrder(Long id, OrderRequestModel orderRequestModel) {
//        Order updateOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("order not found"));
//        updateOrder.setUser(updateOrder.getUser());
//        for (OrderItems items : updateOrder.getOrderItems()) {
//            Product product = items.getProduct();
//            product.setInventory(backProduct(product, items));
//            productRepository.save(product);
//        }
//        updateOrder.getOrderItems().clear();
//        List<OrderItems> orderItemsList = new ArrayList<>();
//
//        Long totalAmount = 0L;
//        for (OrderItemsRequestModel orderItemsRequestModel : orderRequestModel.getOrderItems()) {
//
//            Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow(()->new ProductNotFoundException("product not found"));
//            Long quantity = orderItemsRequestModel.getQuantity();
//            productService.checkQuantity(product, quantity);
//
//            totalAmount += product.getPrice() * quantity;
//            product.setInventory(product.getInventory() - quantity);
//            productRepository.save(product);
//            OrderItems orderItems = OrderItems.orderItemsBuilder().order(updateOrder).quantity(quantity).product(product).amount(totalAmount).build();
//            orderItemsList.add(orderItems);
//        }
//
//
//        updateOrder.setOrderItems(orderItemsList);
//        updateOrder.setTotalAmount(totalAmount);
//        orderRepository.save(updateOrder);
//        return OrderResponseModel.builder().order(updateOrder).build();
//    }

    public String orderDelete(Long id) {
        Order order = orderRepository.findOrderById(id).orElseThrow(() -> new OrderNotFoundException("order not found"));
        List<OrderItems> orderItemsList = orderItemsRepository.findByOrderListId(order.getId());
        orderItemsRepository.deleteAll(orderItemsList);
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
        if (!order.isPayment()) {
            Long totalAmount = order.getTotalAmount();
            walletService.deduceWallet(order, totalAmount);
            order.setPaymentAt(LocalDateTime.now());
            order.setPayment(true);
            orderRepository.save(order);
        }
        return "your order was paid";
    }


    @Transactional
    public OrderResponseModel checkOutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("cart not found"));
        List<CartItems> cartItemsList = cartItemsRepository.findByCartListId(cart.getId());

        User user = userRepository.findById(cart.getUser().getId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Address address = addressRepository.findAddressByUserId(user.getId()).orElseThrow(() -> new AddressException("address not found"));
        Order order = Order.orderBuilder().user(user).address(address).build();

        Long totalAmount = 0L;
        for (CartItems cartItems : cartItemsList) {
            Product product = cartItems.getProduct();
            Long quantity = cartItems.getQuantity();
            productService.checkQuantity(product, quantity);
            Long itemPrice = productService.itemPrice(product, quantity);
            totalAmount += itemPrice;
            productService.updateQuantity(product, quantity);
            OrderItems orderItems = OrderItems.orderItemsBuilder().amount(itemPrice).product(product).quantity(quantity).build();
            orderItemsRepository.save(orderItems);
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        List<OrderItems> orderItemsList = orderItemsRepository.findByOrderListId(order.getId());

        for (OrderItems orderItems : orderItemsList) {
            orderItems.setOrder(order);
        }

        cartItemsRepository.deleteAll(cartItemsList);
        cartRepository.deleteById(cart.getId());

        return OrderResponseModel.builder().order(order).build();
    }
}
