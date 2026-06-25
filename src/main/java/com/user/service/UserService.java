package com.user.service;

import com.common.exception.ConflictException;
import com.common.exception.UnauthorizedException;
import com.user.dto.UserLoginResponse;
import com.user.dto.request.UserLoginRequest;
import com.user.dto.request.UserRegistrationRequest;
import com.user.model.User;
import com.user.repository.UserRepository;
import com.utils.JwtUtil;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

  @Inject UserRepository userRepository;

  @Transactional
  public User register(UserRegistrationRequest request) {
    userRepository
        .findByEmail(request.email)
        .ifPresent(
            existing -> {
              throw new ConflictException("Email already registered");
            });
    User user = new User();
    user.username = request.username;
    user.email = request.email;
    user.password = BcryptUtil.bcryptHash(request.password);
    user.isActive = true;

    userRepository.persist(user);
    return user;
  }

  public UserLoginResponse login(UserLoginRequest request) {

    User user =
        userRepository
            .findByEmail(request.email)
            .orElseThrow(() -> new UnauthorizedException("Email Incorrect email"));

    if (!BcryptUtil.matches(request.password, user.password)) {
      throw new UnauthorizedException("Incorrect password");
    }

    String jwtToken = JwtUtil.generateJwt(user);
    UserLoginResponse userLoginResponse = new UserLoginResponse(user, jwtToken);

    return userLoginResponse;
  }
}
