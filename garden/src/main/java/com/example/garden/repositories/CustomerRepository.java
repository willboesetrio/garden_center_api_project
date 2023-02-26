package com.example.garden.repositories;

import com.example.garden.entities.Customer;
import com.example.garden.entities.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  public List<Customer> findByAddress_Street(String street);
  public List<Customer>  findByAddress_City(String City);
  public List<Customer> findByAddress_State(String state);
  public List<Customer>  findByAddress_Zip(Integer zip);
  public boolean existsByEmail(String email);

  public Customer findByEmail(String email);


}
