package com.example.garden.services;

import com.example.garden.entities.Items;
import com.example.garden.entities.Order;
import com.example.garden.entities.Users;
import com.example.garden.exceptions.ResourceNotFound;
import com.example.garden.exceptions.ServiceUnavailable;
import com.example.garden.repositories.CustomerRepository;
import com.example.garden.repositories.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderServiceImpl implements OrderService{

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public List<Order> queryOrders(Order order) {

    try {

      if (order.isEmpty()) {
        return orderRepository.findAll();
      } else {
        Example<Order> OrderExample = Example.of(order);
        return orderRepository.findAll(OrderExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  public List<Order> getOrdersByProductId(Long productId){

    return orderRepository.findByItems_ProductId(productId);
  }


  public List<Order> getOrdersByQuantity(Integer quantity){

    return orderRepository.findByItems_Quantity(quantity);
  }

  @Override
  public Order getOrderById(Long id){
    try{
      Order orderLookUpResult = orderRepository.findById(id).orElse(null);
      if(orderLookUpResult != null){
        return orderLookUpResult;
      }
    } catch (Exception e){
      throw new ServiceUnavailable(e);
    }
    throw new ResourceNotFound("Could not locate a order with the id: " + id);
  }

  @Override
  public Order addOrder(Order order) {
    //Check required fields
    if (order.getOrderTotal() == null ||
        order.getDate() == null ||
        order.getCustomerId() == null ||
        order.getItems().size() == 0 ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //Check if customer exists in database
    if(customerRepository.existsById(order.getCustomerId()) == false) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid Customer ID");
    }

    //check total for 2 digits
    if(order.getOrderTotal().scale() != 2) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price must have 2 decimal places");
    }

    for (Items i : order.getItems()) {
      i.setOrder(order);
    }
    try{
      return orderRepository.save(order);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }


  }

  @Override
  public Order updateOrderById(Long id, Order order) {

    // first, check to make sure the id passed matches
    if (!order.getId().equals(id)) {
      throw new ResourceNotFound("ID does not match");
    }

    //Check required fields
    if (order.getOrderTotal() == null ||
        order.getDate() == null ||
        order.getCustomerId() == null ||
        order.getItems().size() == 0 ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //Check if customer exists in database
    if(customerRepository.existsById(order.getCustomerId()) == false) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid Customer ID");
    }

    //check total for 2 digits
    if(order.getOrderTotal().scale() != 2) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price must have 2 decimal places");
    }

    for (Items i : order.getItems()) {
      i.setOrder(order);
    }
    try {
      return orderRepository.save(order);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

  }

  @Override
  public void deleteOrder(Long id) {

      try {
        if (orderRepository.existsById(id)) {
          orderRepository.deleteById(id);
          return;
        }
      } catch (Exception e) {
        throw new ServiceUnavailable(e);
      }

      // if we made it down to this point, we did not find the Product
      throw new ResourceNotFound("Could not locate an Order with the id: " + id);
    }

  }

