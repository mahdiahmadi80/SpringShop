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
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
import org.example.springshop.repository.OrderRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.service.serviceint.OrderInt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderInt {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final WalletService walletService;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserService userService, WalletService walletService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.walletService = walletService;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderResponseModel> orderList() {
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();

        orderRepository.findAll().forEach(order -> {
            OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(order).build();
            orderResponseModels.add(orderResponseModel);

        });
        return orderResponseModels;
    }

    @Transactional
    @Override
    @SneakyThrows
    public OrderResponseModel orderAdd(OrderRequestModel orderRequestModel) {
        User user = userRepository.findById(orderRequestModel.getUserid()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Product product = productRepository.findById(orderRequestModel.getProductid()).orElseThrow(() -> new ProductNotExist("product not enough"));

        Order newOrder = Order.orderBuilder().user(user).product(product).build();
        if (user.getWallet().getBalance() < product.getProductPrice()) {
            throw new NotEnoughMoneyException(" your wallet Balance is enough for buy this product");
        }

        if (product.getProductExist() <= 0) {
            throw new ProductNotExist("product not enough");
        }
        Order order = null;
        if (user.getWallet().getBalance() >= product.getProductPrice() && product.getProductExist() > 0) {

            Long pay = user.getWallet().getBalance() - product.getProductPrice();

            Wallet wallet = walletService.findById(user.getWallet().getId());
            wallet.setBalance(pay);

            Long buy = product.getProductExist() - 1;

            product.setProductExist(buy);

            order = Order.orderBuilder().product(product).user(user).build();
        }

        if (order == null) {
            throw new OrderAddFailException("order value is false");
        }

        OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(order).build();
        orderRepository.save(order);
        return orderResponseModel;
    }

    @Override
    public Order orderEdit(Long id, OrderRequestModel orderRequestModel) {
        Order oldOrder = orderRepository.findById(id).orElseThrow(() -> new OrdetNotFoundException("order not found"));

        User newUser = userRepository.findById(orderRequestModel.getUserid()).orElseThrow(() -> new UserNotFoundException("user not found"));

        Product newProduct = productRepository.findById(orderRequestModel.getProductid()).orElseThrow(() -> new ProductNotFoundException("product not found"));

        oldOrder.setUser(newUser);
        oldOrder.setProduct(newProduct);

        Order newOrder = null;
        if (newUser.getWallet().getBalance() > newProduct.getProductPrice() && newProduct.getProductExist() > 0) {
            newOrder = orderRepository.save(oldOrder);
        }

        return orderRepository.save(newOrder);
    }


    @Override
    public void orderDelete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrdetNotFoundException("order not found"));

        Wallet buyWallet = walletService.findById(order.getUser().getWallet().getId());

        Product buyproduct = productRepository.findById(order.getProduct().getId()).orElseThrow(() -> new ProductNotFoundException("product not found"));

        Long backMoney = order.getUser().getWallet().getBalance() + buyproduct.getProductPrice();
        buyWallet.setBalance(backMoney);

        Long backProduct = order.getProduct().getProductExist() + 1;

        buyproduct.setProductExist(backProduct);
        productRepository.save(buyproduct);

        orderRepository.deleteById(id);
    }
}
