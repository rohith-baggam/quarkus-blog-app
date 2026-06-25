package com.blog.resource;

import com.blog.dto.BlogCreateResponse;
import com.blog.dto.request.BlogCreateRequest;
import com.blog.services.BlogGenericServices;
import com.common.response.ApiResponse;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/blog-generic-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogGenericResource {

  @Inject BlogGenericServices blogGenericServices;

  @POST
  @Authenticated
  @Path("/create-post")
  public Response createPost(@Valid BlogCreateRequest request) {
    BlogCreateResponse blogCreateResponse = blogGenericServices.createPost(request);
    return ApiResponse.success(blogCreateResponse, "Blog Post create successfully");
  }
}
