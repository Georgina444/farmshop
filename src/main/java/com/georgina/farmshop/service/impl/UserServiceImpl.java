package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.configuration.JwtProvider;
import com.georgina.farmshop.model.*;
import com.georgina.farmshop.repository.*;
import com.georgina.farmshop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;
  private final WishlistRepository wishlistRepository;
  private final ReviewRepository reviewRepository;
  private final TransactionRepository transactionRepository;

  @Override
  public User findUserByJwtToken(String jwt) throws Exception {

    String email = jwtProvider.getEmailFromJwtToken(jwt);
    return this.findUserByEmail(email);
  }

  @Override
  public User findUserByEmail(String email) throws Exception {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new Exception("User not found with this email: " + email);
    }
    return user;
  }

  @Override
  public User findUserById(Long id) throws Exception {
    return userRepository.findById(id)
        .orElseThrow(() -> new Exception("User not found with id: " + id));
  }

  @Override
  public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Override
  @Transactional
  public void deleteUserById(Long id) throws Exception {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new Exception("User not found: " + id));
    userRepository.delete(user);
  }

}
