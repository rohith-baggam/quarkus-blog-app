package com.blog.dto;

import java.util.UUID;

public class BlogUpdateResponse {

  public UUID postId;

  public String title;

  public String description;

  public BlogUpdateResponse(UUID postId, String title, String descrption) {
    this.postId = postId;
    this.title = title;
    this.description = descrption;
  }
}
