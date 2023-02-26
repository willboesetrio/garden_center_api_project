package com.example.garden.services;

import com.example.garden.entities.Customer;
import com.example.garden.entities.Order;
import java.util.List;

public interface OrderService {

  List<Order> queryOrders(Order order);
  List<Order> getOrdersByProductId(Long productId);
  List<Order> getOrdersByQuantity(Integer quantity);
  Order getOrderById(Long id);
  Order addOrder(Order order);

  Order updateOrderById(Long id, Order order);
  void deleteOrder(Long id);

}
