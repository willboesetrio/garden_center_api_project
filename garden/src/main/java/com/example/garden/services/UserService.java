package com.example.garden.services;

import com.example.garden.entities.Users;
import java.util.List;

public interface UserService {

  List<Users> queryUsers(Users Users);
  Users getUser(Long id);

  Users addUser(Users User);

  Users updateUserById(Long id, Users User);

  void deleteUser(Long id);

}
