package com.example.garden.services;

import com.example.garden.entities.Product;
import com.example.garden.entities.Users;
import java.util.List;

public interface ProductService {

  List<Product> queryProducts(Product Product);

  Product getProduct(Long id);

  Product addProduct(Product Product);

  Product updateProductById(Long id, Product Product);

  void deleteProduct(Long id);

}
