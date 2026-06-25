package com.common.response;

import jakarta.ws.rs.core.Response;

public class ApiResponse<T> {

  public String message;
  public T response;

  public ApiResponse() {}

  public static <T> Response success(T response, String message) {
    ApiResponse<T> result = new ApiResponse<>();
    result.message = message;
    result.response = response;
    return Response.status(Response.Status.OK).entity(result).build();
  }

  public static <T> Response error(T response, String message) {
    ApiResponse<T> result = new ApiResponse<>();
    result.message = message;
    result.response = response;
    return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
  }
}
