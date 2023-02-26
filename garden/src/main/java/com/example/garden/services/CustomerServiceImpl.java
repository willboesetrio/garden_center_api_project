package com.example.garden.services;

import com.example.garden.entities.Customer;
import com.example.garden.entities.Users;
import com.example.garden.exceptions.ResourceNotFound;
import com.example.garden.exceptions.ServiceUnavailable;
import com.example.garden.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public List<Customer> queryCustomers(Customer customer) {
    try {

      if (customer.isEmpty()) {
        return customerRepository.findAll();
      } else {
        Example<Customer> CustomerExample = Example.of(customer);
        return customerRepository.findAll(CustomerExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public List<Customer> findByAddress_Street(String street) {
    return customerRepository.findByAddress_Street(street);
  }

  @Override
  public List<Customer> findByAddress_State(String state) {
    return customerRepository.findByAddress_State(state);
  }

  @Override
  public List<Customer> findByAddress_City(String city) {
    return customerRepository.findByAddress_City(city);
  }

  @Override
  public List<Customer> findByAddress_Zip(Integer zip) {
    return customerRepository.findByAddress_Zip(zip);
  }

  @Override
  public Customer getCustomer(Long id) {
    try {
      Customer CustomerLookupResult = customerRepository.findById(id).orElse(null);
      if (CustomerLookupResult != null) {
        return CustomerLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }

  @Override
  public Customer addCustomer(Customer customer) {

    if (customer.getEmail() == null ||
        customer.getName() == null ||
        customer.getAddress().getCity() == null ||
        customer.getAddress().getState() == null ||
        customer.getAddress().getZip() == null ||
        customer.getAddress().getStreet() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //check email
      if (customer.getEmail().matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}") == false) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid email");
      }

    if (!customerRepository.existsByEmail(customer.getEmail())) {
      try {
        return customerRepository.save(customer);
      } catch (Exception e) {
        throw new ServiceUnavailable(e);
      }
    }
    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
  }

  @Override
  public Customer updateCustomerById(Long id, Customer customer) {
    // first, check to make sure the id passed matches the id in the Vaccination passed
    if (!customer.getId().equals(id)) {
      throw new ResourceNotFound("ID does not match");
    }

    if (customerRepository.existsById(customer.getId()) == false) {
      throw new ResourceNotFound("ID does not exist");
    }

    if (customer.getEmail() == null ||
        customer.getName() == null ||
        customer.getAddress().getCity() == null ||
        customer.getAddress().getState() == null ||
        customer.getAddress().getZip() == null ||
        customer.getAddress().getStreet() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //check email
    if (customer.getEmail().matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}") == false) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid email");
    }

    //check if email already exists
    //if incoming email DOES not match existing email
    //if (!customer.getEmail()
     //   .equals(customerRepository.findByEmail(customer.getEmail()).getEmail())) {
    Customer currentCustomer = customerRepository.findById(id).orElse(null);
    boolean isSameEmail = customer.getEmail().equals(currentCustomer.getEmail());

      if (customerRepository.existsByEmail(customer.getEmail()) && !isSameEmail) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
      }
      //}

      try {
        Customer CustomerFromDb = customerRepository.findById(id).orElse(null);
        if (CustomerFromDb != null) {
          return customerRepository.save(customer);
        }
      } catch (Exception e) {
        throw new ServiceUnavailable(e);
      }

      // if we made it down to this point, we did not find the User
      throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
    }

  @Override
  public void deleteCustomer(Long id) {

    try {
      if (customerRepository.existsById(id)) {
        customerRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this point, we did not find the customer
    throw new ResourceNotFound("Could not locate a Customer with the id: " + id);
  }

}


