package com.user.resource;

import com.common.response.ApiResponse;
import com.user.dto.UserLoginResponse;
import com.user.dto.UserResponse;
import com.user.dto.request.UserLoginRequest;
import com.user.dto.request.UserRegistrationRequest;
import com.user.model.User;
import com.user.service.UserService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAuthResource {
    @Inject
    UserService userService;

    @POST
    @Path("/register")
    public Response register(@Valid UserRegistrationRequest request) {

        try {
            User user = userService.register(request);
            UserResponse userResponse = UserResponse.from(user);
            return ApiResponse.success(userResponse, "User registered successfully");

        } catch (IllegalArgumentException e) {
            return ApiResponse.error(null, "Email already exist");
        }

    }

    @POST
    @Path("/login")
    public Response login(
            @Valid UserLoginRequest request) {

        try {
            UserLoginResponse userLoginResponse = userService.login(request);
            return ApiResponse.success(userLoginResponse, "Login successfully");
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(null, "Invalid credentials");
        }

    }
}
