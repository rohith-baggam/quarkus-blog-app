package com.utils;

import com.user.model.User;
import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.util.Set;

public class JwtUtil {

  public static String generateJwt(User user) {
    String jwtToken =
        Jwt.issuer("blog-app")
            .subject(user.id.toString())
            .claim("email", user.email)
            .groups(Set.of("user"))
            .expiresIn(Duration.ofDays(7))
            .sign();
    return jwtToken;
  }
}
