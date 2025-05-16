package com.georgina.farmshop.request;

import com.georgina.farmshop.domain.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   // Spring needs it to turn JSON into DTO
@AllArgsConstructor
public class LoginOtpRequest {

    private String email;
    private String otp;
    private UserRoles role;

}
