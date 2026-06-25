package com.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationRequest {

  @NotBlank
  @Size(max = 128)
  public String username;

  @NotBlank(message = "Email is required")
  @Size(max = 128)
  public String email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be atleast 8 characters")
  public String password;
}
