package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Order;
import com.georgina.farmshop.model.PaymentOrderEntity;
import com.georgina.farmshop.model.User;
import com.stripe.exception.StripeException;

import java.util.Set;

public interface PaymentService {

  PaymentOrderEntity createOrder(User user, Set<Order> orders);

  PaymentOrderEntity getPaymentOrderById(Long orderId) throws Exception;

  PaymentOrderEntity getPaymentOrderByPaymentId(String orderId) throws Exception;

  Boolean ProceedPaymentOrder(PaymentOrderEntity paymentOrderEntity,
      String paymentId,
      String paymentLinkId);

  String createStripePaymentLink(User user,
      Long amount,
      Long orderId) throws StripeException;
}
