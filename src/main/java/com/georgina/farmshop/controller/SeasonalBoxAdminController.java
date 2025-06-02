// src/main/java/com/georgina/farmshop/controller/SeasonalBoxAdminController.java
package com.georgina.farmshop.controller;

import com.georgina.farmshop.dto.SeasonalBoxModel;
import com.georgina.farmshop.model.response.ApiResponse;
import com.georgina.farmshop.service.SeasonalBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/seasonal-boxes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SeasonalBoxAdminController {
  private final SeasonalBoxService boxService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SeasonalBoxModel create(@RequestBody SeasonalBoxModel m) {
    return boxService.createBox(m);
  }

  @PatchMapping("/{id}")
  public SeasonalBoxModel update(
      @PathVariable Long id,
      @RequestBody SeasonalBoxModel m
  ) throws Exception {
    return boxService.updateBox(m, id);
  }

  @DeleteMapping("/{id}")
  public ApiResponse delete(@PathVariable Long id) throws Exception {
    boxService.deleteBox(id);
    ApiResponse res = new ApiResponse();
    res.setMessage("Seasonal box deleted successfully");
    return res;
  }
}
