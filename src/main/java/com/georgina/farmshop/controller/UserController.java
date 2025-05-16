package com.georgina.farmshop.controller;

import com.georgina.farmshop.model.User;
import com.georgina.farmshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // fetching user from db
    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandler(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {


        User user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }
}
