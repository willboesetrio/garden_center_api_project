package com.example.garden.repositories;

import com.example.garden.entities.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByItems_ProductId(Long id);
  List<Order> findByItems_Quantity(Integer quantity);


}
