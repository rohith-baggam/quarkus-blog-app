package com.blog.dto;

import com.blog.model.BlogPost;
import com.user.dto.UserResponse;
import java.util.List;
import java.util.UUID;

public class BlogPaginatedResponse {
  public UUID id;
  public String title;
  public String description;
  public UserResponse userResponse;
  public List<BlogPostImageResponse> blogImages;

  public BlogPaginatedResponse(
      BlogPost blogPost, UserResponse userResponse, List<BlogPostImageResponse> blogImages) {
    this.id = blogPost.id;
    this.title = blogPost.title;
    this.description = blogPost.description;
    this.userResponse = userResponse;
    this.blogImages = blogImages;
  }
}
