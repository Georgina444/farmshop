package com.georgina.farmshop.controller;


import com.georgina.farmshop.domain.AccountStatus;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AdminController {

  private final SellerService sellerService;


  @PatchMapping("/seller/{id}/status/{status}")
  public ResponseEntity<Seller> updateSellerStatus(
      @PathVariable Long id,
      @PathVariable AccountStatus status) throws Exception {

    Seller updatedSeller = sellerService.updateSellerAccountStatus(id, status);
    return ResponseEntity.ok(updatedSeller);
  }
}
