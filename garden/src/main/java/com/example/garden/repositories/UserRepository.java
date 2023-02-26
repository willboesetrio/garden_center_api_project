package com.example.garden.repositories;

import com.example.garden.entities.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

public boolean existsByEmail(String email);

  public List<Users> findByEmail(String email);

}
