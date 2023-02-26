package com.example.garden.services;

import com.example.garden.entities.Customer;
import com.example.garden.entities.Product;
import com.example.garden.entities.Users;
import com.example.garden.exceptions.ResourceNotFound;
import com.example.garden.exceptions.ServiceUnavailable;
import com.example.garden.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService{

  @Autowired
  ProductRepository productRepository;

  @Override
  public List<Product> queryProducts(Product Product) {
    try {

      if (Product.isEmpty()) {
        return productRepository.findAll();
      } else {
        Example<Product> ProductExample = Example.of(Product);
        return productRepository.findAll(ProductExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Product getProduct(Long id) {
    try {
      Product ProductLookupResult = productRepository.findById(id).orElse(null);
      if (ProductLookupResult != null) {
        return ProductLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a Product with the id: " + id);
  }

  @Override
  public Product addProduct(Product Product) {

    if (Product.getName() == null ||
        Product.getManufacturer() == null ||
        Product.getSku() == null ||
        Product.getPrice() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //check price for 2 digits
    if(Product.getPrice().scale() != 2) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price must have 2 decimal places");
    }

    if (!productRepository.existsBySku(Product.getSku())) {
      try {
        return productRepository.save(Product);
      } catch (Exception e) {
        throw new ServiceUnavailable(e);
      }
    }
    throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU is already in use");
  }

  @Override
  public Product updateProductById(Long id, Product Product) {

    // first, check to make sure the id passed matches
    if (!Product.getId().equals(id)) {
      throw new ResourceNotFound("ID does not match");
    }

    if (productRepository.existsById(Product.getId()) == false) {
      throw new ResourceNotFound("ID does not exist");
    }

    //check if fields are valid
    if(Product.getPrice() == null ||
        Product.getSku() == null ||
        Product.getName() == null ||
        Product.getManufacturer() == null ||
        Product.getType() == null)
    {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //check SKU
    Product currentProduct = productRepository.findById(id).orElse(null);
    boolean isSameSku = Product.getSku().equals(currentProduct.getSku());

    if (productRepository.existsBySku(Product.getSku()) && !isSameSku) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU is already in use");
    }

    //check price for 2 digits
    if(Product.getPrice().scale() != 2) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price must have 2 decimal places");
    }

    try {
      Product ProductFromDb = productRepository.findById(id).orElse(null);
      if (ProductFromDb != null) {
        return productRepository.save(Product);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this point, we did not find the Product
    throw new ResourceNotFound("Could not locate a Product with the id: " + id);
  }

  @Override
  public void deleteProduct(Long id) {

    try {
      if (productRepository.existsById(id)) {
        productRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this point, we did not find the Product
    throw new ResourceNotFound("Could not locate a Product with the id: " + id);
  }


}
