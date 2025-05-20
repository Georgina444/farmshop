package com.georgina.farmshop.service;


import com.georgina.farmshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

  User findUserByJwtToken(String jwt) throws Exception;

  User findUserByEmail(String email) throws Exception;

  User findUserById(Long id) throws Exception;

  Page<User> getAllUsers(Pageable pageable) throws Exception;

  void deleteUserById(Long id) throws Exception;

}
