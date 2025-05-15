package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.configuration.JwtProvider;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.repository.UserRepository;
import com.georgina.farmshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found with this email: " + email);
        }
        return user;
    }
}
