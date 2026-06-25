package com.user.dto;

import java.util.UUID;

import com.user.model.User;

public class UserResponse {

    public UUID id;
    public String username;
    public String email;
    public boolean isActive;

    public UserResponse(User user) {

        this.id = user.id;
        this.username = user.username;
        this.email = user.email;

    }

}
