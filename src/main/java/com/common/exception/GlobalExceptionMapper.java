package com.common.exception;

import com.common.response.ApiResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof ConflictException) {
            return ApiResponse.error(null, exception.getMessage());
        }
        if (exception instanceof UnauthorizedException) {
            return ApiResponse.error(null, exception.getMessage());
        }
        if (exception instanceof ResourceNotFoundException) {
            return ApiResponse.error(null, exception.getMessage());
        }

        return ApiResponse.error(null, "An unexpected error occurred");
    }
}
