package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.configuration.JwtProvider;
import com.georgina.farmshop.domain.UserRoles;
import com.georgina.farmshop.model.Cart;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.model.VerificationCode;
import com.georgina.farmshop.repository.CartRepository;
import com.georgina.farmshop.repository.SellerRepository;
import com.georgina.farmshop.repository.UserRepository;
import com.georgina.farmshop.repository.VerificationCodeRepository;
import com.georgina.farmshop.request.LoginRequest;
import com.georgina.farmshop.response.AuthResponse;
import com.georgina.farmshop.response.SignUpRequest;
import com.georgina.farmshop.service.AuthService;
import com.georgina.farmshop.service.EmailService;
import com.georgina.farmshop.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    private final SellerRepository sellerRepository;

    @Override
    public void sentLoginOrSignUpOtp(String email, UserRoles role) throws Exception {
        String SIGNING_PREFIX="signing_";


        if(email.startsWith(SIGNING_PREFIX)){
            email=email.substring(SIGNING_PREFIX.length());

            if(role.equals(UserRoles.ROLE_SELLER)) {
                Seller seller = sellerRepository.findByEmail(email);
                if(seller==null){
                    throw new Exception("Seller not found.");
                }
            }
            else{ // customer or admin
                User user = userRepository.findByEmail(email);
                if (user == null) {
                    throw new Exception("User not found.");
                }
            }
        }

        VerificationCode doesExist = verificationCodeRepository.findByEmail(email);

        if(doesExist!=null){
            verificationCodeRepository.delete(doesExist);
        }

       String otp= OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject="Georgina's login/signup otp";
        String text ="Your login otp is: " + otp;

        emailService.sendVerificationOtpEmail(email,otp,subject,text);
    }

    @Override
    public String createUser(SignUpRequest request) throws Exception {

        // checking if verification code is present in our db
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail());

        if(verificationCode==null || !verificationCode.getOtp().equals(request.getOtp())){
            throw new Exception("Wrong OTP...");
        }

        User user = userRepository.findByEmail(request.getEmail());

        // Maps DTO -> entity
        if(user==null){
            User createdUser = new User();
            createdUser.setEmail(request.getEmail());
            createdUser.setFullName(request.getFullName());
            createdUser.setRole(UserRoles.ROLE_CUSTOMER);
            createdUser.setPhoneNumber("0876638293");
            createdUser.setPassword(passwordEncoder.encode(request.getOtp()));

            // saves user to db
            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRoles.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest request) throws Exception {
        String username = request.getEmail();
        String otp = request.getOtp();

        Authentication authentication = authenticate(username,otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login success!");

        Collection<?extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(UserRoles.valueOf(roleName));
        return authResponse;
    }

    // here we verify otp
    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";
        if (username.startsWith(SELLER_PREFIX)) {
            username = username.substring(SELLER_PREFIX.length());
        }

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username.");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        // checking if it is the same as in the db
        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong otp.");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
    }
}
