package com.georgina.farmshop.model;


import com.georgina.farmshop.domain.PaymentStatus;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsEntity {

  private String paymentId;
  private PaymentStatus status;
}
