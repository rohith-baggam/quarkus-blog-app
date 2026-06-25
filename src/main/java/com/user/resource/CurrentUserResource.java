package com.user.resource;

import com.common.response.ApiResponse;
import com.common.security.CurrentUser;
import com.user.dto.UserResponse;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users/me")
@Produces(MediaType.APPLICATION_JSON)
public class CurrentUserResource {

  @Inject CurrentUser currentUser;

  @GET
  @Authenticated
  public Response me() {
    UserResponse userResponse = new UserResponse(currentUser.getUser());
    return ApiResponse.success(userResponse, "Your successfully authenticated");
  }
}
