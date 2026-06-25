package com.blog.dto;

import java.util.UUID;

public class BlogCreateResponse {
  public UUID postId;
  public String title;

  public BlogCreateResponse(UUID postId, String title) {
    this.postId = postId;
    this.title = title;
  }
}
