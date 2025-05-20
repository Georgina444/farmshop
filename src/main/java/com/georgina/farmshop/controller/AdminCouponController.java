package com.georgina.farmshop.controller;


import com.georgina.farmshop.model.Coupon;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.service.CartService;
import com.georgina.farmshop.service.CouponService;
import com.georgina.farmshop.service.UserService;
import com.georgina.farmshop.model.Cart;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class AdminCouponController {

  private final CouponService couponService;
  private final UserService userService;
  private final CartService cartService;

  @PostMapping("/apply")
  public ResponseEntity<Cart> applyCoupon(
      @RequestParam String apply,
      @RequestParam String code,
      @RequestParam double orderValue,
      @RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Cart cart;

    if (apply.equals("true")) {
      cart = couponService.applyCoupon(code, orderValue, user);
    } else {
      cart = couponService.removeCoupon(code, user);
    }
    return ResponseEntity.ok(cart);
  }

  // Admin operations
  @PostMapping("/admin/create")
  public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
    Coupon createdCoupon = couponService.createCoupon(coupon);
    return ResponseEntity.ok(createdCoupon);
  }

  @DeleteMapping("admin/delete/{id}")
  public ResponseEntity<?> deleteCoupon(@PathVariable Long id) throws Exception {
    couponService.deleteCoupon(id);
    return ResponseEntity.ok("Coupon deleted successfully.");
  }

  @GetMapping("/admin/all")
  public ResponseEntity<List<Coupon>> getAllCoupons() {
    List<Coupon> coupons = couponService.findAllCoupons();
    return ResponseEntity.ok(coupons);
  }


}
