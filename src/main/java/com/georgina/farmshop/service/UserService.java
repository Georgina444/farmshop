package com.georgina.farmshop.service;


import com.georgina.farmshop.model.User;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;

}
