package com.blog.dto.request;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import java.util.UUID;

public class BlogListParams {

  @QueryParam("limit")
  @DefaultValue("10")
  public int limit;

  @QueryParam("offset")
  @DefaultValue("10")
  public int offset;

  @QueryParam("authorId")
  public UUID authorId;
}
