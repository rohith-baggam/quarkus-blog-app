package com.user.dto;

import java.util.UUID;

import com.user.model.User;

public class UserResponse {

    public UUID id;
    public String username;
    public String email;
    public boolean isActive;

    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        return response;
    }

}
