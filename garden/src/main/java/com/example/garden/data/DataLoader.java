package com.example.garden.data;

import com.example.garden.entities.Address;
import com.example.garden.entities.Customer;
//import com.example.garden.entities.Item;
//import com.example.garden.entities.Order;
import com.example.garden.entities.Items;
import com.example.garden.entities.Order;
import com.example.garden.entities.Product;
import com.example.garden.entities.Users;
import com.example.garden.repositories.CustomerRepository;
//import com.example.garden.repositories.OrderRepository;
import com.example.garden.repositories.ItemsRepository;
import com.example.garden.repositories.OrderRepository;
import com.example.garden.repositories.ProductRepository;
import com.example.garden.repositories.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  ItemsRepository itemsRepository;

  private Users user1;
  private Users user2;

  private Customer customer1;
  private Customer customer2;
  private Customer customer3;
  private Customer customer4;

  private Product product1;
  private Product product2;
  private Product product3;
  private Product product4;

  private Order order1;
  private Order order2;
  private Items item1;
  private Items item2;
  private Items item3;
  private Items item4;


  @Override
  public void run(String... args) throws Exception {
    loadUsers();
    loadCustomers();
    loadProducts();
    loadOrders();
    loadItems();
  }

  public void loadUsers() {
    user1 = userRepository.save(new Users("Will T", "title", new String[]{"EMPLOYEE"}, "name@email.com", "password1"));
    user2 = userRepository.save(new Users("Will B", "title2", new String[]{"ADMIN"}, "wboese@email.com", "rootroot2"));
  }
  public void loadCustomers() {
    customer1 = customerRepository.save(new Customer( "Will", "will@email.com", new Address("1234 NW Bobcat Lane", "St. Robert", "MO", 65584)));
    customer2 = customerRepository.save(new Customer( "Greg", "greg@email.com", new Address("711 Nulla St", "Mankato", "MI", 96522)));
    customer3 = customerRepository.save(new Customer( "Calista", "calista@email.com", new Address("729 Dictum Ave", "San Antonio", "TX", 47096)));
    customer4 = customerRepository.save(new Customer( "Hubert", "hubert@email.com", new Address("103 Thunder Road", "Charlotte", "NC", 21823)));
  }

  public void loadProducts() {
    product1 = productRepository.save(new Product("SKU1234", "type1", "WD40", "manufacturer1", new BigDecimal(3.99)));
    product2 = productRepository.save(new Product("SKU4321", "type2", "suet cake", "manufacturer2", new BigDecimal(4.59)));
    product3 = productRepository.save(new Product("SKU5678", "type3", "tap and die", "manufacturer1", new BigDecimal(19.99)));
    product4 = productRepository.save(new Product("SKU999", "type4", "raid", "manufacturer2", new BigDecimal(12.99)));
  }

 public void loadOrders() {
    order1 = orderRepository.save(new Order(customer1.getId(),
        Date.from(LocalDate.parse("2001-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant()),
        BigDecimal.valueOf(999.99)
        ));

   order2 = orderRepository.save(new Order(customer2.getId(),
       Date.from(LocalDate.parse("2010-04-07").atStartOfDay(ZoneId.systemDefault()).toInstant()),
       BigDecimal.valueOf(256.49)
   ));
 }

 public void loadItems() {
   item1 = itemsRepository.save(new Items(product1.getId(), 10, order1));
   item2 = itemsRepository.save(new Items(product2.getId(), 5, order1));
   item3 = itemsRepository.save(new Items(product3.getId(), 3, order2));
   item4 = itemsRepository.save(new Items(product4.getId(), 7, order2));
 }

}
