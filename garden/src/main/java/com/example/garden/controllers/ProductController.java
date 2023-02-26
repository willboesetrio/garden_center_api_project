package com.example.garden.controllers;


import com.example.garden.entities.Product;
import com.example.garden.entities.Users;
import com.example.garden.services.ProductService;
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
@RequestMapping("/products")
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping
  public ResponseEntity<List<Product>> queryProducts(Product Product) {
    return new ResponseEntity<>(productService.queryProducts(Product), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Product> save(@Valid @RequestBody Product Product) {
    return new ResponseEntity<>(productService.addProduct(Product), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Product> updateProductById(@PathVariable Long id,
      @Valid @RequestBody Product Product) {
    return new ResponseEntity<>(productService.updateProductById(id, Product),
        HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteProductById(@PathVariable Long id) {
    productService.deleteProduct(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
