package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Order;
import com.georgina.farmshop.model.PaymentDetails;
import com.georgina.farmshop.model.PaymentOrder;
import com.georgina.farmshop.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;

import java.util.Set;

public interface PaymentService {

    PaymentOrder createOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;
    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,
                                String paymentId,
                                String paymentLinkId);

    String createStripePaymentLink(User user,
                                   Long amount,
                                   Long orderId) throws StripeException;
}
