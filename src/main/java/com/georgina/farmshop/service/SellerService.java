package com.georgina.farmshop.service;

import com.georgina.farmshop.domain.AccountStatus;
import com.georgina.farmshop.exceptions.SellerException;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.response.AuthResponse;

import java.util.List;

public interface SellerService {

  Seller getSellerProfile(String jwt) throws Exception;

  Seller createSeller(Seller seller) throws Exception;

  Seller getSellerById(Long id) throws SellerException;

  Seller getSellerByEmail(String email) throws Exception;

  List<Seller> getAllSellers(AccountStatus status);

  Seller updateSeller(Long id, Seller seller) throws Exception;

  void deleteSeller(Long id) throws Exception;

  Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception;

  AuthResponse loginSeller(String email, String otp) throws Exception;

  Seller verifyEmail(String otp) throws Exception;


}
