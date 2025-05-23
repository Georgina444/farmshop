package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.model.Order;
import com.georgina.farmshop.model.PaymentOrderEntity;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.repository.OrderRepository;
import com.georgina.farmshop.repository.PaymentOrderRepository;
import com.georgina.farmshop.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


  private PaymentOrderRepository paymentOrderRepository;
  private OrderRepository orderRepository;

  private String stripeSecretKey = "stripesecretkey";

  @Override
  public PaymentOrderEntity createOrder(User user, Set<Order> orders) {
    Long amount = orders.stream().mapToLong(Order::getTotalSellingPrice).sum();

    PaymentOrderEntity paymentOrderEntity = new PaymentOrderEntity();
    paymentOrderEntity.setAmount(amount);
    paymentOrderEntity.setUser(user);
    paymentOrderEntity.setOrders(orders);

    return paymentOrderRepository.save(paymentOrderEntity);
  }

  @Override
  public PaymentOrderEntity getPaymentOrderById(Long orderId) throws Exception {

    return paymentOrderRepository.findById(orderId).orElseThrow(() ->
        new Exception("Payment order not found."));
  }

  @Override
  public PaymentOrderEntity getPaymentOrderByPaymentId(String orderId) throws Exception {
    PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findByPaymentLinkId(orderId);

    if (paymentOrderEntity == null) {
      throw new Exception("Payment order not found.");
    }
    return paymentOrderEntity;
  }

  @Override
  public Boolean ProceedPaymentOrder(PaymentOrderEntity paymentOrderEntity,
      String paymentId,
      String paymentLinkId) {

//        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
//            StripeClient stripeClient = new StripeClient(stripeSecretKey);
//
//            Stripe payment = stripeClient.
    return null;
  }

  @Override
  public String createStripePaymentLink(User user, Long amount, Long orderId)
      throws StripeException {
    Stripe.apiKey = stripeSecretKey;

    SessionCreateParams params = SessionCreateParams.builder()
        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
        .setCancelUrl("http://localhost:3000/payment-cancel/")
        .addLineItem(SessionCreateParams.LineItem.builder()
            .setQuantity(1L)
            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("USD")
                .setUnitAmount(amount * 100)  // cents
                .setProductData(
                    SessionCreateParams
                        .LineItem.PriceData.ProductData
                        .builder().setName("Farm shop payment")
                        .build()
                ).build()
            ).build()
        ).build();

    Session session = Session.create(params);
    return session.getUrl();  // make the payment here
  }
}
