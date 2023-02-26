package com.example.garden.services;

import com.example.garden.entities.Customer;
import com.example.garden.entities.Users;
import java.util.List;

public interface CustomerService {

  List<Customer> queryCustomers(Customer customer);

  List<Customer> findByAddress_Street(String street);

  List<Customer> findByAddress_State(String state);

  List<Customer> findByAddress_City(String city);

  List<Customer> findByAddress_Zip(Integer zip);

  Customer getCustomer(Long id);

  Customer addCustomer(Customer customer);

  Customer updateCustomerById(Long id, Customer Customer);

  void deleteCustomer(Long id);

}
