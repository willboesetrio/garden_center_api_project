package com.example.garden.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import javax.validation.Valid;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;


  private String email;


  @OneToOne(cascade = CascadeType.ALL)
  @Valid
  private Address address;


  public Customer() {
  }

  public Customer(String name, String email, Address address) {
    this.name = name;
    this.email = email;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return Objects.equals(id, customer.id) && Objects.equals(name, customer.name)
        && Objects.equals(email, customer.email) && Objects.equals(address,
        customer.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, address);
  }

  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(email) &&
        Objects.isNull(address);
  }

}
