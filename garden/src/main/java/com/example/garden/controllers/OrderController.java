package com.example.garden.controllers;


import com.example.garden.entities.Customer;
import com.example.garden.entities.Order;
import com.example.garden.entities.Product;
import com.example.garden.services.OrderService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @GetMapping
  public ResponseEntity<List<Order>> queryOrders(Order order) {
    return new ResponseEntity<>(orderService.queryOrders(order), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id){
    return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
  }


  @GetMapping(params = "productId")
  public ResponseEntity<List<Order>> findOrdersByProductId(Long productId){
    return new ResponseEntity<>(orderService.getOrdersByProductId(productId), HttpStatus.OK);
  }


  @GetMapping(params = "quantity")
  public ResponseEntity<List<Order>> findOrdersByQuantity(Integer quantity){
    return new ResponseEntity<>(orderService.getOrdersByQuantity(quantity),HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Order> save(@Valid @RequestBody Order order){
    return new ResponseEntity<>(orderService.addOrder(order),HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Order> updateOrderById(@PathVariable Long id,
      @Valid @RequestBody Order Order) {
    return new ResponseEntity<>(orderService.updateOrderById(id, Order),
        HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteOrderById(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
