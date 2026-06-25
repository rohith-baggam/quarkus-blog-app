package com.common.security;

import com.user.model.User;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class CurrentUser {
  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
