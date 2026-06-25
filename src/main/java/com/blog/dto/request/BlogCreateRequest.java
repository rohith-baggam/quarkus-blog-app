package com.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class BlogCreateRequest {
  @NotBlank(message = "Title is required field")
  @Size(min = 5, max = 256)
  public String title;

  @Size(max = 512)
  public String description;

  public List<String> postImageUrls;
}
