package com.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginRequest {
    @NotBlank(message = "Email is a required field")
    @Size(max = 128)
    public String email;
    @NotBlank(message = "Password is a required field")
    @Size(min = 8, max = 128)
    public String password;

}
