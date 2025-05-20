package com.georgina.farmshop.controller;

import com.georgina.farmshop.model.User;
import com.georgina.farmshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  // fetching user from db
  @GetMapping("/profile")
  public ResponseEntity<User> createUserHandler(
      @RequestHeader("Authorization") String jwt
  ) throws Exception {

    User user = userService.findUserByJwtToken(jwt);

    return ResponseEntity.ok(user);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception {
    User user = userService.findUserById(id);
    return ResponseEntity.ok(user);
  }

  @GetMapping
  public ResponseEntity<Page<User>> getAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) throws Exception {
    Page<User> users = userService.getAllUsers(PageRequest.of(page, size));
    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable Long id) throws Exception {
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}
