package org.example.springshop.service.serviceint;

import org.example.springshop.model.Order;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.OrderRequestModel;
import org.example.springshop.model.dto.responsemodel.OrderResponseModel;

import java.util.List;

public interface OrderInt {
    List<OrderResponseModel> orderList();

    Order orderAdd(OrderRequestModel orderRequestModel);

    Order orderEdit(Long id, OrderRequestModel orderRequestModel);

    User userSearch(Long id);

    void orderDelete(Long id);
}
