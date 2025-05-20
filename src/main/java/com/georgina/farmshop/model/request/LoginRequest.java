package com.georgina.farmshop.model.request;

import lombok.Data;

@Data
public class LoginRequest {

  private String email;
  private String otp;
}
