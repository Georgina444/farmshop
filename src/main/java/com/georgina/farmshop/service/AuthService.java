package com.georgina.farmshop.service;

import com.georgina.farmshop.domain.UserRoles;
import com.georgina.farmshop.model.request.LoginRequest;
import com.georgina.farmshop.model.response.AuthResponse;
import com.georgina.farmshop.model.response.SignUpRequest;

public interface AuthService {

  // He added user role for seller but do I need it for the admin account to?o(String email, USER_ROLE role)
  void sentLoginOrSignUpOtp(String email, UserRoles role) throws Exception;

  String createUser(SignUpRequest request) throws Exception;

  AuthResponse signing(LoginRequest request) throws Exception;
}
