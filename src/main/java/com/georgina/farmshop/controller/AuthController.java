package com.georgina.farmshop.controller;

import com.georgina.farmshop.domain.UserRoles;
import com.georgina.farmshop.repository.UserRepository;
import com.georgina.farmshop.model.request.LoginOtpRequest;
import com.georgina.farmshop.model.request.LoginRequest;
import com.georgina.farmshop.model.response.ApiResponse;
import com.georgina.farmshop.model.response.AuthResponse;
import com.georgina.farmshop.model.response.SignUpRequest;
import com.georgina.farmshop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")   // Base path for all endpoints in this controller
@RequiredArgsConstructor
public class AuthController {

  // a Spring data JPA that handles all CRUD operations for User + you can check for email, save, update etc.
  private final UserRepository userRepository;  // shortcut to User table in db
  // set of steps for signing up someone, send OTPs, checking passwords, making JWTs.
  private final AuthService authService;  // shortcut to signup/login business logic and

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignUpRequest request)
      throws Exception {

    String jwt = authService.createUser(request);

    AuthResponse response = new AuthResponse();
    response.setJwt(jwt);
    response.setMessage("Successful registration");
    response.setRole(UserRoles.ROLE_CUSTOMER);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/sent/login-signup-otp") // we are creating new records in the db
  public ResponseEntity<ApiResponse> sendOtpHandler(
      @RequestBody LoginOtpRequest request) throws Exception {

    authService.sentLoginOrSignUpOtp(request.getEmail(), request.getRole());

    ApiResponse response = new ApiResponse();

    response.setMessage("Otp sent successfully");

    return ResponseEntity.ok(response);
  }


  @PostMapping("/signing") // we are creating new records in the db
  public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request)
      throws Exception {

    AuthResponse authResponse = authService.signing(request);

    return ResponseEntity.ok(authResponse);
  }


}
