package com.example.garden.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String sku;

  private String type;

  private String name;

  private String manufacturer;

  private BigDecimal price;

  public Product() {

  }

  public Product(String sku, String type, String name, String manufacturer, BigDecimal price) {
    this.sku = sku;
    this.type = type;
    this.name = name;
    this.manufacturer = manufacturer;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return id.equals(product.id) && sku.equals(product.sku) && type.equals(product.type)
        && name.equals(product.name) && manufacturer.equals(product.manufacturer) && price.equals(
        product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sku, type, name, manufacturer, price);
  }

  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(sku) &&
        Objects.isNull(type) &&
        Objects.isNull(manufacturer) &&
        Objects.isNull(price);
  }

}
