package com.common.security;

import com.user.model.User;
import com.user.repository.UserRepository;
import io.quarkus.security.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.util.UUID;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
  @Inject JsonWebToken jwt;
  @Inject UserRepository userRepository;
  @Inject CurrentUser currentUser;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    String subject = jwt.getSubject();

    if (subject == null) {
      return;
    }
    UUID userId = UUID.fromString(subject);
    User user =
        userRepository
            .find("id", userId)
            .firstResultOptional()
            .orElseThrow(() -> new UnauthorizedException("User no longer active"));

    if (!user.isActive) {
      throw new UnauthorizedException("User account is inactive");
    }
    currentUser.setUser(user);
  }
}
