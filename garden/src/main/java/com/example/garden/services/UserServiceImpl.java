package com.example.garden.services;

import com.example.garden.entities.Customer;
import com.example.garden.entities.Users;
import com.example.garden.exceptions.ResourceNotFound;
import com.example.garden.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.garden.exceptions.ServiceUnavailable;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository userRepository;


  public List<Users> queryUsers(Users User) {

    try {

      if (User.isEmpty()) {
        return userRepository.findAll();
      } else {
        Example<Users> UserExample = Example.of(User);
        return userRepository.findAll(UserExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  @Override
  public Users getUser(Long id) {
    try {
      Users UserLookupResult = userRepository.findById(id).orElse(null);
      if (UserLookupResult != null) {
        return UserLookupResult;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }

  @Override
  public Users addUser(Users User) {

    if(User.getEmail() == null ||
        User.getPassword() == null ||
        User.getName() == null ||
        User.getTitle() == null ||
        User.getRoles() == null)
    {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //check password length
    if (User.getPassword().length() <= 8) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password must be at least 8 characters");
    }

    //check email
    if (User.getEmail().matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}") == false) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid email");
    }

    if (!userRepository.existsByEmail(User.getEmail())) {
      try {
        return userRepository.save(User);
      } catch (Exception e) {
        throw new ServiceUnavailable(e);
      }
   }
    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
  }

  public Users updateUserById(Long id, Users User) {

    // first, check to make sure the id passed matches
    if (!User.getId().equals(id)) {
      throw new ResourceNotFound("ID does not match");
    }

    if (userRepository.existsById(User.getId()) == false) {
      throw new ResourceNotFound("ID does not exist");
    }

    //check if fields are valid
    if(User.getEmail() == null ||
        User.getPassword() == null ||
        User.getName() == null ||
        User.getTitle() == null ||
        User.getRoles() == null)
    {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields are required");
    }

    //check password length
    if (User.getPassword().length() <= 8) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password must be at least 8 characters");
    }
    //check email
    if (User.getEmail().matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}") == false) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid email");
    }

    //check if email already exists
    //if incoming email DOES not match existing email

    Users currentUser = userRepository.findById(id).orElse(null);
    boolean isSameEmail = User.getEmail().equals(currentUser.getEmail());

      if (userRepository.existsByEmail(User.getEmail()) && !isSameEmail) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
      }

    try {
      Users UserFromDb = userRepository.findById(id).orElse(null);
      if (UserFromDb != null) {
        return userRepository.save(User);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this point, we did not find the User
    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }

  @Override
  public void deleteUser(Long id) {

    try {
      if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it down to this point, we did not find the User
    throw new ResourceNotFound("Could not locate a User with the id: " + id);
  }


}





