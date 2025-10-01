package org.example.springshop.service;

import org.example.springshop.exception.orderException.OrderNotFoundException;
import org.example.springshop.model.Order;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;
import org.example.springshop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {


    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    void testSearchById_whenOrderExists_shouldReturnOrderResponse() {
        User user = new User();
        user.setId(1L);
// مقداردهی سایر فیلدهای لازم user

        Order order = new Order();
        order.setId(10L);
        order.setUser(user);

        OrderResponseModel result = orderService.searchById(order.getId());

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
    }


    @Test
    void testSearchById_whenOrderDoesNotExist_shouldThrowException() {
        Long orderId = 99L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.searchById(orderId));

    }

}
