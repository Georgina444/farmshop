package com.georgina.farmshop.controller;

import com.georgina.farmshop.dto.DealModel;
import com.georgina.farmshop.model.response.ApiResponse;
import com.georgina.farmshop.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class DealController {

  private final DealService dealService;

  // list all deals
  @GetMapping
  public ResponseEntity<List<DealModel>> getAllDeals() {
    List<DealModel> deals = dealService.getDeals();
    return ResponseEntity.ok(deals);
  }

  // create a new deal from dto
  @PostMapping
  public ResponseEntity<DealModel> createDeal(@RequestBody DealModel dealModel) {
    DealModel created = dealService.createDeal(dealModel);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  // update existing deal from dto
  @PatchMapping("/{id}")
  public ResponseEntity<DealModel> updateDeal(
      @PathVariable Long id,
      @RequestBody DealModel dealModel
  ) throws Exception {
    DealModel updated = dealService.updateDeal(dealModel, id);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) throws Exception {
    dealService.deleteDeal(id);
    ApiResponse res = new ApiResponse();
    res.setMessage("Deal deleted successfully.");
    return ResponseEntity.ok(res);
  }
}

