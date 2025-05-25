package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.model.Order;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
import org.example.springshop.repository.OrderRepository;
import org.example.springshop.repository.ProductRepository;
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

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserService userService, WalletService walletService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.walletService = walletService;
    }

    @Override
    public List<OrderResponseModel> orderList() {
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
                    OrderResponseModel orderResponseModel = OrderResponseModel.builder().order(order).build();
                    orderResponseModels.add(orderResponseModel);
                }
        );
        return orderResponseModels;
    }

    @Transactional
    @Override
    public Order orderAdd(OrderRequestModel orderRequestModel) {

        User user = userService.findById(orderRequestModel.getUserid());

//        User user = userRepository.findById(orderRequestModel.getUserid()).orElseThrow();
        Product product = productRepository.findById(orderRequestModel.getProductid()).orElseThrow();


        Order order = null;
        if (user.getWallet().getBalance() >= product.getProductPrice() && product.getProductExist() > 0) {

            Long pay = user.getWallet().getBalance() - product.getProductPrice();
            Wallet wallet = walletService.findById(user.getWallet().getId());
//            Wallet wallet = walletRepository.findById(user.getWallet().getId()).orElseThrow();
            wallet.setBalance(pay);

            Long buy = product.getProductExist() - 1;

            product.setProductExist(buy);
            order = Order.OrderBuilder().product(product).user(user).build();
        }


        return orderRepository.save(order);
    }

    @Override
    public Order orderEdit(Long id, OrderRequestModel orderRequestModel) {
        Order oldOrder = orderRepository.findById(id).orElseThrow();
User newUser= userService.findById(orderRequestModel.getUserid());
//        User newUser = userRepository.findById(orderRequestModel.getUserid()).orElseThrow();

        oldOrder.setUser(newUser);

        Product newProduct = productRepository.findById(orderRequestModel.getProductid()).orElseThrow();

        oldOrder.setProduct(newProduct);

        Order newOrder = null;
        if (newUser.getWallet().getBalance() > newProduct.getProductPrice() && newProduct.getProductExist() > 0) {
            newOrder = orderRepository.save(oldOrder);
        }
        return orderRepository.save(newOrder);
    }

    @Override
    public User userSearch(Long id) {
        return orderRepository.findById(id).orElseThrow().getUser();
    }

    @Override
    public void orderDelete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
Wallet buyWallet = walletService.findById(order.getUser().getWallet().getId());
//        Wallet buyWallet = walletRepository.findById(order.getUser().getWallet().getId()).orElseThrow();

        Product buyproduct = productRepository.findById(order.getProduct().getId()).orElseThrow();

        Long backMoney = order.getUser().getWallet().getBalance() + buyproduct.getProductPrice();
        buyWallet.setBalance(backMoney);

        Long backProduct = order.getProduct().getProductExist() + 1;

        buyproduct.setProductExist(backProduct);
        productRepository.save(buyproduct);


        orderRepository.deleteById(id);
    }
}
