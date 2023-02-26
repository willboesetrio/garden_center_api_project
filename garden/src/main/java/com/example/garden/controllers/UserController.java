package com.example.garden.controllers;

import com.example.garden.entities.Users;
import com.example.garden.services.UserService;
import java.util.Date;
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
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;


  @GetMapping
  public ResponseEntity<List<Users>> queryUsers(Users Users) {
    return new ResponseEntity<>(userService.queryUsers(Users), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Users> getUser(@PathVariable Long id) {
    return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Users> save(@Valid @RequestBody Users User) {
    return new ResponseEntity<>(userService.addUser(User), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Users> updateUserById(@PathVariable Long id,
      @Valid @RequestBody Users User) {
    return new ResponseEntity<>(userService.updateUserById(id, User),
        HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteUserById(@PathVariable Long id) {
    userService.deleteUser(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
