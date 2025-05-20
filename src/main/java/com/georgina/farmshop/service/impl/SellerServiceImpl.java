package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.configuration.JwtProvider;
import com.georgina.farmshop.domain.AccountStatus;
import com.georgina.farmshop.domain.UserRoles;
import com.georgina.farmshop.exceptions.SellerException;
import com.georgina.farmshop.model.Address;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.VerificationCode;
import com.georgina.farmshop.repository.AddressRepository;
import com.georgina.farmshop.repository.SellerRepository;
import com.georgina.farmshop.repository.VerificationCodeRepository;
import com.georgina.farmshop.model.response.AuthResponse;
import com.georgina.farmshop.service.EmailService;
import com.georgina.farmshop.service.SellerService;
import com.georgina.farmshop.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

  private final SellerRepository sellerRepository;
  private final JwtProvider jwtProvider;
  private final AddressRepository addressRepository;
  private final VerificationCodeRepository verificationCodeRepository;
  private final EmailService emailService;

  @Override
  public Seller getSellerProfile(String jwt) throws Exception {
    String email = jwtProvider.getEmailFromJwtToken(jwt);

    return this.getSellerByEmail(email);
  }

  @Override
  public Seller createSeller(Seller seller) throws Exception {

    if (sellerRepository.findByEmail(seller.getEmail()) != null) {
      throw new SellerException("Seller already exists with this email.");
    }

    Address address = addressRepository.save(seller.getPickupAddress());
    seller.setPickupAddress(address);
    seller.setRole(UserRoles.ROLE_SELLER);
    seller.setAccountStatus(AccountStatus.PENDING_VERIFICATION);
    seller.setEmailVerified(false);

    Seller saved = sellerRepository.save(seller);

    String otp = OtpUtil.generateOtp();
    VerificationCode vc = new VerificationCode();
    vc.setEmail(saved.getEmail());
    vc.setOtp(otp);
    verificationCodeRepository.save(vc);

    String subject = "Farm shop email verification code";
    String text = "Welcome! Your OTP is: " + otp;
    System.out.printf(">>> [createSeller] Generated OTP %s for %s%n", otp, saved.getEmail());
    emailService.sendVerificationOtpEmail(saved.getEmail(), otp, subject, text);

    return saved;
  }

  @Override
  public Seller getSellerById(Long id) throws SellerException {
    return sellerRepository.findById(id)
        .orElseThrow(() -> new SellerException("Seller not found with this id." + id));
  }

  @Override
  public Seller getSellerByEmail(String email) throws Exception {
    Seller seller = sellerRepository.findByEmail(email);
    if (seller == null) {
      throw new Exception("Seller not found!");
    }
    return seller;
  }

  @Override
  public List<Seller> getAllSellers(AccountStatus status) {
    return sellerRepository.findByAccountStatus(status);
  }

  @Override
  public Seller updateSeller(Long id, Seller seller) throws Exception {
    Seller existingSeller = this.getSellerById(id);

    if (seller.getSellerName() != null) {
      existingSeller.setSellerName(seller.getSellerName());
    }
    if (seller.getPhoneNumber() != null) {
      existingSeller.setPhoneNumber(seller.getPhoneNumber());
    }
    if (seller.getEmail() != null) {
      existingSeller.setEmail(seller.getEmail());
    }
    if (seller.getBusinessDetails() != null
        && seller.getBusinessDetails().getBusinessName() != null
    ) {
      existingSeller.getBusinessDetails().setBusinessName(
          seller.getBusinessDetails().getBusinessName()
      );
    }
    if (seller.getBankDetailsEntity() != null
        && seller.getBankDetailsEntity().getAccountHolderName() != null
        && seller.getBankDetailsEntity().getIfscCode() != null
        && seller.getBankDetailsEntity().getAccountNumber() != null
    ) {
      existingSeller.getBankDetailsEntity().setAccountHolderName(
          seller.getBankDetailsEntity().getAccountHolderName()
      );
      existingSeller.getBankDetailsEntity().setAccountNumber(
          seller.getBankDetailsEntity().getAccountNumber()
      );
      existingSeller.getBankDetailsEntity().setIfscCode(
          seller.getBankDetailsEntity().getIfscCode()
      );
    }
    // MADE CHANGES HERE BECAUSE THE ADDRESS REFUSED TO UPDATE BECAUSE THE FIELDS WERE NULL FROM CREATION
    if (seller.getPickupAddress() != null) {
      Address incoming = seller.getPickupAddress();
      Address existing = existingSeller.getPickupAddress();
        if (incoming.getAddress() != null) {
            existing.setAddress(incoming.getAddress());
        }
        if (incoming.getCity() != null) {
            existing.setCity(incoming.getCity());
        }
        if (incoming.getCountry() != null) {
            existing.setCountry(incoming.getCountry());
        }
        if (incoming.getPinCode() != null) {
            existing.setPinCode(incoming.getPinCode());
        }
        if (incoming.getPhoneNumber() != null) {
            existing.setPhoneNumber(incoming.getPhoneNumber());
        }
    }
    {
      existingSeller.getPickupAddress()
          .setAddress(seller.getPickupAddress().getAddress());
      existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
      existingSeller.getPickupAddress().setCountry(seller.getPickupAddress().getCountry());
      existingSeller.getPickupAddress().setPhoneNumber(seller.getPickupAddress().getPhoneNumber());
      existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
    }
    if (seller.getGSTIN() != null) {
      existingSeller.setGSTIN(seller.getGSTIN());
    }

    return sellerRepository.save(existingSeller);
  }

  @Override
  public void deleteSeller(Long id) throws Exception {
    Seller seller = getSellerById(id);
    sellerRepository.delete(seller);
  }

  @Override
  public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
    Seller seller = getSellerById(sellerId);
    seller.setAccountStatus(status);
    return sellerRepository.save(seller);
  }

  @Override
  public AuthResponse loginSeller(String email, String otp) throws Exception {
    // String sellerEmail = "seller_" + email;
    Seller seller = sellerRepository.findByEmail(email);
    if (seller == null) {
      throw new SellerException("Seller not found with email: " + email);
    }

    VerificationCode code = verificationCodeRepository.findByEmail(seller.getEmail());
    if (code == null || !code.getOtp().equals(otp)) {
      throw new SellerException("Invalid OTP");
    }

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
            seller.getEmail(),
            null,
            List.of(new SimpleGrantedAuthority(seller.getRole().toString()))
        );
    String jwt = jwtProvider.generateToken(authToken);

    AuthResponse resp = new AuthResponse();
    resp.setJwt(jwt);
    resp.setRole(seller.getRole());
    resp.setMessage("Login successful");
    return resp;
  }

  @Override
  public Seller verifyEmail(String otp) throws Exception {
    VerificationCode code = verificationCodeRepository.findByOtp(otp);
    if (code == null || !code.getOtp().equals(otp)) {
      throw new SellerException("Invalid OTP");
    }

    Seller seller = sellerRepository.findByEmail(code.getEmail());
    if (seller == null) {
      throw new SellerException("Seller not found with email: " + code.getEmail());
    }
    seller.setEmailVerified(true);
    return sellerRepository.save(seller);
  }
}
