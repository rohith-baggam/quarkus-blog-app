package com.blog.dto;

import java.util.UUID;

public class BlogUpdateResponse {

  public UUID id;

  public String title;

  public String description;

  public BlogUpdateResponse(UUID id, String title, String descrption) {
    this.id = id;
    this.title = title;
    this.description = descrption;
  }
}
