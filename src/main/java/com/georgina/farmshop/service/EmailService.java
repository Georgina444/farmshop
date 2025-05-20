package com.georgina.farmshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

  void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text);
}
