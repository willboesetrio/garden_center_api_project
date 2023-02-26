package com.example.garden.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;
  @Column
  private String title;
  @Column
  private String[] roles;
  @Column
  //@Email(regexp = "/^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/g", message = "invalid email") --did not work
  private String email;
  @Column(length = 99)
  //@Size(min = 8, max = 99, message = "password must be at least 8 characters") -- also did not work
  private String password;

  public Users(){

  }

  public Users(String name, String title, String[] roles, String email, String password) {
    this.name = name;
    this.title = title;
    this.roles = roles;
    this.email = email;
    this.password = password;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public  String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Users users = (Users) o;
    return Objects.equals(id, users.id) && Objects.equals(name, users.name)
        && Objects.equals(title, users.title) && Arrays.equals(roles, users.roles)
        && Objects.equals(email, users.email) && Objects.equals(password,
        users.password);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, name, title, email, password);
    result = 31 * result + Arrays.hashCode(roles);
    return result;
  }

  public boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(name) &&
        Objects.isNull(title) &&
        Objects.isNull(email) &&
        Objects.isNull(password) &&
        Objects.isNull(roles);
  }


}