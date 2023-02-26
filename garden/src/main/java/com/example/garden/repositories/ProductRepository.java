package com.example.garden.repositories;

import com.example.garden.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  public boolean existsBySku(String sku);

}
