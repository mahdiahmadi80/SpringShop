package org.example.springshop.service;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.example.springshop.exception.orderException.NotEnoughMoneyException;
import org.example.springshop.exception.orderException.OrderAddFailException;
import org.example.springshop.exception.orderException.OrdetNotFoundException;
import org.example.springshop.exception.productException.ProductNotExist;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
import org.example.springshop.repository.OrderRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<OrderResponseModel> orderList() {
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(order).build();
            orderResponseModels.add(orderResponseModel);
        });
        return orderResponseModels;
    }

    @Transactional
    @SneakyThrows
    public OrderResponseModel orderAdd(OrderRequestModel orderRequestModel) {
        User user = userRepository.findById(orderRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Product product = productRepository.findById(orderRequestModel.getProductId()).orElseThrow(() -> new ProductNotExist("product not enough"));
        Long count = orderRequestModel.getCount();
        walletCheck(user, totalPrice(product, count));
        quantityCheck(product, count);
        newQuantity(product, count);
        newBalance(user, totalPrice(product, count));
        Order newOrder = Order.orderBuilder().user(user).build();
        OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(newOrder).build();
        orderRepository.save(newOrder);
        return orderResponseModel;
    }

    public Long totalPrice(Product product, Long count) {
        return product.getPrice() * count;
    }

    public void quantityCheck(Product product, Long count) {
        if (count > product.getInventory()) {
            throw new ProductNotExist("your count over than quantity");
        }
    }

    public void walletCheck(User user, Long totalPrice) {
        long walletBalance = user.getWallet().getBalance() - totalPrice;
        if (walletBalance < 0) {
            throw new NotEnoughMoneyException();
        }
    }

    public void newQuantity(Product product, Long count) {
        product.setInventory(product.getInventory() - count);
    }

    public void newBalance(User user, Long totalPrice) {
        user.getWallet().setBalance(user.getWallet().getBalance() - totalPrice);
    }


    public OrderResponseModel orderEdit(Long id, OrderRequestModel orderRequestModel) {
        Order editOrder = orderRepository.findById(id).orElseThrow(() -> new OrdetNotFoundException("order not found"));
        User newUser = userRepository.findById(orderRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Product newProduct = productRepository.findById(orderRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
        editOrderCheck(newUser, newProduct);
        editOrder.setUser(newUser);

        orderRepository.save(editOrder);
        return OrderResponseModel.builder().order(editOrder).build();
    }

    private void editOrderCheck(User newUser, Product newProduct) {
        if (newUser.getWallet().getBalance() < newProduct.getPrice() && newProduct.getInventory() < 0) {
            throw new OrderAddFailException("value is false");
        }
    }

    public String orderDelete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrdetNotFoundException("order not found"));


        return "your order deleted";
    }

    public void backBalance(Order order, Long totalPrice) {
        Long balance = order.getUser().getWallet().getBalance() + totalPrice;
        order.getUser().getWallet().setBalance(balance);
    }

    public Long backProduct(Order order) {
        return 2L+3L;
    }
}
