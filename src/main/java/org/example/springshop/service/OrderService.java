package org.example.springshop.service;
import jakarta.transaction.Transactional;
import org.example.springshop.exception.orderException.NotEnoughMoneyException;
import org.example.springshop.exception.orderException.OrderAddFailException;
import org.example.springshop.exception.orderException.OrdetNotFoundException;
import org.example.springshop.exception.productException.ProductNotExist;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.exception.walletException.BalanceException;
import org.example.springshop.model.Order;
import org.example.springshop.model.OrderItems;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.OrderItemsRequestModel;
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
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public OrderResponseModel addOrder(OrderRequestModel orderRequestModel) {

        User user = userRepository.findById(orderRequestModel.getUserId()).orElseThrow();


        List<OrderItems> orderItemsList = new ArrayList<>();

        Order order = Order.orderBuilder().user(user).orderItems(orderItemsList).build();

        Long totalAmount = 0L;
        for (OrderItemsRequestModel orderItemsRequestModel : orderRequestModel.getOrderItems()) {

            Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow();
            Long quantity = orderItemsRequestModel.getQuantity();
            checkQuantity(product, quantity);

            totalAmount += totalPrice(product, quantity);
            OrderItems orderItems = OrderItems.orderItemsBuilder().order(order).product(product).orderItemsRequestModel(orderItemsRequestModel).build();
            updateQuantity(product, quantity);
            orderItemsList.add(orderItems);
        }

        checkWallet(user, totalAmount);
        newBalance(user, totalAmount);
        order.setOrderItems(orderItemsList);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        return OrderResponseModel.builder().order(order).build();
    }


    public Long totalPrice(Product product, Long quantity) {
        return product.getPrice() * quantity;
    }

    public Boolean checkQuantity(Product product, Long count) {
        if (count > product.getInventory()) {
            throw new ProductNotExist("your count over than quantity");
        }
        return false;
    }

    public void checkWallet(User user, Long totalPrice) {
        long walletBalance = user.getWallet().getBalance() - totalPrice;
        if (walletBalance < 0) {
            throw new NotEnoughMoneyException();
        }
    }

    public void updateQuantity(Product product, Long count) {
        product.setInventory(product.getInventory() - count);
        productRepository.save(product);

    }

    public void newBalance(User user, Long totalPrice) {
        user.getWallet().setBalance(user.getWallet().getBalance() - totalPrice);
        userRepository.save(user);
    }

    @Transactional
    public OrderResponseModel editOrder(Long id, OrderRequestModel orderRequestModel) {
        Order updateOrder = orderRepository.findById(id).orElseThrow(() -> new OrdetNotFoundException("order not found"));
        User updateUser = userRepository.findById(orderRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));

        for (OrderItems items : updateOrder.getOrderItems()) {
            Product product = items.getProduct();
            product.setInventory(product.getInventory() + items.getQuantity());
            productRepository.save(product);
            updateUser.getWallet().setBalance(updateUser.getWallet().getBalance() + (product.getPrice() * items.getQuantity()));
        }

        updateOrder.getOrderItems().clear();
        userRepository.save(updateUser);

        List<OrderItems> orderItemsList = new ArrayList<>();
        Long totalAmount = 0L;
        for (OrderItemsRequestModel orderItemsRequestModel : orderRequestModel.getOrderItems()) {
            Product product = productRepository.findById(orderItemsRequestModel.getProductId()).orElseThrow();
            if (orderItemsRequestModel.getQuantity() > product.getInventory()) {

            }
            totalAmount += product.getPrice() * orderItemsRequestModel.getQuantity();

            product.setInventory(product.getInventory() - orderItemsRequestModel.getQuantity());
            productRepository.save(product);

            OrderItems orderItems = OrderItems.orderItemsBuilder().order(updateOrder).product(product).orderItemsRequestModel(orderItemsRequestModel).build();
            orderItemsList.add(orderItems);
        }

        if (updateUser.getWallet().getBalance() < totalAmount) {
            throw new BalanceException("balance not enough");
        }
        updateUser.getWallet().setBalance(updateUser.getWallet().getBalance() - totalAmount);
        userRepository.save(updateUser);

        updateOrder.setUser(updateUser);
        updateOrder.setOrderItems(orderItemsList);
        updateOrder.setTotalAmount(totalAmount);
        orderRepository.save(updateOrder);
        return OrderResponseModel.builder().order(updateOrder).build();
    }

    private void editOrderCheck(User newUser, Product newProduct) {
        if (newUser.getWallet().getBalance() < newProduct.getPrice() && newProduct.getInventory() < 0) {
            throw new OrderAddFailException("value is false");
        }
    }

    public String orderDelete(Long id) {
        orderRepository.deleteById(id);
        return "your order deleted";
    }

    public String backBalance(Order order, Long totalPrice) {
        Long balance = order.getUser().getWallet().getBalance() + totalPrice;
        order.getUser().getWallet().setBalance(balance);
        return "your money deposit in your wallet";
    }

    public Long backProduct(Order order) {
        return 2L + 3L;
    }

    public OrderResponseModel searchById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return OrderResponseModel.builder().order(order).build();
    }
//@Transactional
//    public OrderResponseModel editOrder(Long id, OrderRequestModel orderRequestModel) {
//        Order updateOrder = orderRepository.findById(id).orElseThrow();
//        User updateUser = userRepository.findById(orderRequestModel.getUserId()).orElseThrow();
//        List<OrderItems> orderItemsList = orderItemsRepository.findByOrderItemList(orderRequestModel.getOrderItems());
//
//        updateOrder.setOrderItems(orderItemsList);
//        updateOrder.setUser(updateUser);
//        Order order = orderRepository.save(updateOrder);
//        return OrderResponseModel.builder().order(order).build();
//    }
}
