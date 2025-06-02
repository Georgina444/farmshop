// src/main/java/com/georgina/farmshop/controller/SeasonalBoxController.java
package com.georgina.farmshop.controller;

import com.georgina.farmshop.dto.SeasonalBoxModel;
import com.georgina.farmshop.model.CartItem;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.service.SeasonalBoxService;
import com.georgina.farmshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasonal-boxes")
@RequiredArgsConstructor
public class SeasonalBoxController {

  private final SeasonalBoxService boxService;
  private final UserService userService;

  @GetMapping
  public List<SeasonalBoxModel> all() {
    return boxService.getAllBoxes();
  }

  @GetMapping("/{id}")
  public SeasonalBoxModel one(@PathVariable Long id) throws Exception {
    return boxService.getBoxById(id);
  }

  @PostMapping("/{id}/cart")
  public CartItem addToCart(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long id,
      @RequestParam(defaultValue = "1") int quantity
  ) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    return boxService.addBoxToCart(user, id, quantity);
  }
}
