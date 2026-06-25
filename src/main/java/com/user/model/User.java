package com.user.model;

import com.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(name = "username", length = 128, nullable = false)
  public String username;

  @Column(name = "email", length = 128, nullable = false, unique = true)
  public String email;

  @Column(name = "password", length = 256, nullable = false)
  public String password; // store encrypted password

  @Column(name = "is_active", nullable = false)
  public boolean isActive = true;
}
