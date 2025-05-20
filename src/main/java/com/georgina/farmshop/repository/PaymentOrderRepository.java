package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrderEntity, Long> {

  PaymentOrderEntity findByPaymentLinkId(String paymentId);
}
