package com.blog.dto;

import com.blog.model.BlogPostImage;
import java.util.UUID;

public class BlogPostImageResponse {
  public UUID id;
  public String imageUrl;

  public BlogPostImageResponse(BlogPostImage blogPostImage) {
    this.id = blogPostImage.id;
    this.imageUrl = blogPostImage.imageUrl;
  }
}
