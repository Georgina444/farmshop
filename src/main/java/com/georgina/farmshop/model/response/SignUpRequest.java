package com.georgina.farmshop.model.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequest {


  private String email;
  private String fullName;
  private String otp;
}
