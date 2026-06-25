package com.user.dto;

import com.user.model.User;
import java.util.UUID;

public class UserLoginResponse {
  public UUID userId;
  public String email;
  public String token;

  public UserLoginResponse(User user, String token) {
    this.userId = user.id;
    this.email = user.email;
    this.token = token;
  }
}
