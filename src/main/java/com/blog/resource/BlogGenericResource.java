package com.blog.resource;

import com.blog.dto.BlogCreateResponse;
import com.blog.dto.BlogPaginatedResponse;
import com.blog.dto.request.BlogCreateRequest;
import com.blog.dto.request.BlogListParams;
import com.blog.services.BlogGenericServices;
import com.common.response.ApiResponse;
import com.common.response.PaginatedListResponse;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/blog-generic-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogGenericResource {

  @Inject BlogGenericServices blogGenericServices;

  @GET
  @Authenticated
  @Path("/post-list-api")
  public Response postList(@BeanParam BlogListParams queryParams) {

    PaginatedListResponse<List<BlogPaginatedResponse>> paginatedListResponse =
        blogGenericServices.getBlogPaginatedList(queryParams);
    return ApiResponse.success(paginatedListResponse, "Post fetched successfully");
  }

  @POST
  @Authenticated
  @Path("/create-post")
  public Response createPost(@Valid BlogCreateRequest request) {
    BlogCreateResponse blogCreateResponse = blogGenericServices.createPost(request);
    return ApiResponse.success(blogCreateResponse, "Blog Post create successfully");
  }

  // @PUT
  // @Authenticated
  // @Path("/update-post")
  // public Response updatedPost(
  // @Valid BlogUpdateRequest request) {

  // BlogUpdateResponse blogUpdateResponse =
  // blogGenericServices.updatePost(request);
  // return ApiResponse.success(blogUpdateResponse, "updated successfully");
  // }
}
