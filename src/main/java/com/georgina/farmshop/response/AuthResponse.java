package com.georgina.farmshop.response;

import com.georgina.farmshop.domain.UserRoles;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserRoles role;
}
