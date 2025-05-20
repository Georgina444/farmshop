package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender javaMailSender;

  @Override
  public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) {
    System.out.printf(">>> SENDING SELLER OTP %s TO %s%n", otp, userEmail);
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
      // to which user
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(text);
      mimeMessageHelper.setTo(userEmail);
      javaMailSender.send(mimeMessage);
    } catch (MailException | MessagingException e) {
      System.out.println("Error!" + e);
      throw new MailSendException("Failed to send email.");
    }
  }
}
