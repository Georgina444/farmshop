package com.georgina.farmshop.response;

import com.georgina.farmshop.domain.USER_ROLES;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLES role;
}
