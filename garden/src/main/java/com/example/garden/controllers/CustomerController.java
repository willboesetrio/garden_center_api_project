package com.example.garden.controllers;


import com.example.garden.entities.Customer;
import com.example.garden.entities.Users;
import com.example.garden.services.CustomerService;
import com.example.garden.services.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;


  @GetMapping
  public ResponseEntity<List<Customer>> queryCustomers(Customer customer) {
    return new ResponseEntity<>(customerService.queryCustomers(customer), HttpStatus.OK);
  }

  @GetMapping(params = "street")
  public ResponseEntity<List<Customer>> findCustomerByStreet(
      @RequestParam("street") String street) {

    return new ResponseEntity<>(customerService.findByAddress_Street(street), HttpStatus.OK);
  }

  @GetMapping(params = "state")
  public ResponseEntity<List<Customer>> findCustomerByState(
      @RequestParam("state") String state) {

    return new ResponseEntity<>(customerService.findByAddress_State(state), HttpStatus.OK);
  }

  @GetMapping(params = "city")
  public ResponseEntity<List<Customer>> findCustomerByCity(
      @RequestParam("city") String city) {

    return new ResponseEntity<>(customerService.findByAddress_City(city), HttpStatus.OK);
  }

  @GetMapping(params = "zip")
  public ResponseEntity<List<Customer>> findCustomerByZip(
      @RequestParam("zip") int zip) {

    return new ResponseEntity<>(customerService.findByAddress_Zip(zip), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Customer> getUser(@PathVariable Long id) {
    return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Customer> save(@Valid @RequestBody Customer Customer) {
    return new ResponseEntity<>(customerService.addCustomer(Customer), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Customer> updateUserById(@PathVariable Long id,
      @Valid @RequestBody Customer customer) {
    return new ResponseEntity<>(customerService.updateCustomerById(id, customer),
        HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteCustomerById(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
