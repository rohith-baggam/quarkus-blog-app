package com.learning;

import java.time.LocalDateTime;
import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/health")
@Tag(name = "Health", description = "Health check endpoint")
public class HealthResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthCheck() {
        HealthReturnResponse response = new HealthReturnResponse(
                "Ok",
                "blog-app",
                LocalDateTime.now().toString());

        return Response.ok(response).build();

    }

}
