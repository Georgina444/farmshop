package com.georgina.farmshop.controller;

import com.georgina.farmshop.configuration.JwtProvider;
import com.georgina.farmshop.domain.AccountStatus;
import com.georgina.farmshop.exceptions.SellerException;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.SellerReport;
import com.georgina.farmshop.repository.SellerRepository;
import com.georgina.farmshop.repository.VerificationCodeRepository;
import com.georgina.farmshop.model.request.LoginRequest;
import com.georgina.farmshop.model.response.AuthResponse;
import com.georgina.farmshop.service.AuthService;
import com.georgina.farmshop.service.EmailService;
import com.georgina.farmshop.service.SellerReportService;
import com.georgina.farmshop.service.SellerService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

  private final SellerService sellerService;
  private final SellerReportService sellerReportService;


  @PostMapping("/login")
  public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req) throws Exception {
    AuthResponse auth = sellerService.loginSeller(req.getEmail(), req.getOtp());
    return ResponseEntity.ok(auth);
  }

  @PatchMapping("/verify/{otp}")
  public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception {
    Seller s = sellerService.verifyEmail(otp);
    return ResponseEntity.ok(s);
  }

  @PostMapping
  public ResponseEntity<Seller> createSeller(
      @RequestBody Seller seller) throws Exception, MessagingException {
    Seller s = sellerService.createSeller(seller);
    return new ResponseEntity<>(s, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
    Seller seller = sellerService.getSellerById(id);
    return new ResponseEntity<>(seller, HttpStatus.OK);
  }

  @GetMapping("/profile")
  public ResponseEntity<Seller> getSellerByJwt(
      @RequestHeader("Authorization") String jwt) throws Exception {

    Seller seller = sellerService.getSellerProfile(jwt);
    return new ResponseEntity<>(seller, HttpStatus.OK);
  }

  @GetMapping("/report")
  public ResponseEntity<SellerReport> getSellerReport(
      @RequestHeader("Authorization") String jwt) throws Exception {

    Seller seller = sellerService.getSellerProfile(jwt);
    SellerReport report = sellerReportService.getSellerReport(seller);
    return new ResponseEntity<>(report, HttpStatus.OK);
  }


  @GetMapping
  public ResponseEntity<List<Seller>> getAllSellers(
      // List<Seller> is the return type - gets serialized to JSON array
      @RequestParam(required = false) AccountStatus status) {
    // delegates to business layer instead of jamming db logic in your controller
    List<Seller> sellers = sellerService.getAllSellers(status);
    // puts sellers list into the body
    // also List<Seller> becomes a JSON array
    return ResponseEntity.ok(sellers);
  }

  @PatchMapping()
  public ResponseEntity<Seller> updateSeller(
      @RequestHeader("Authorization") String jwt,
      @RequestBody Seller seller) throws Exception {

    Seller profile = sellerService.getSellerProfile(jwt);
    Seller updateSeller = sellerService.updateSeller(profile.getId(), seller);
    return ResponseEntity.ok(updateSeller);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {
    sellerService.deleteSeller(id);
    return ResponseEntity.noContent().build();
  }


}
