package com.georgina.farmshop.controller;

import com.georgina.farmshop.model.*;
import com.georgina.farmshop.model.response.ApiResponse;
import com.georgina.farmshop.model.response.PaymentLinkResponse;
import com.georgina.farmshop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

  private final PaymentService paymentService;
  private final UserService userService;
  private final SellerService sellerService;
  private final SellerReportService sellerReportService;
  private final TransactionService transactionService;


  @GetMapping("/{paymentId}")
  public ResponseEntity<ApiResponse> paymentSuccessHandler(
      @PathVariable String paymentId,
      @RequestParam String paymentLinkId,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);

    PaymentLinkResponse paymentResponse;

    PaymentOrderEntity paymentOrderEntity = paymentService
        .getPaymentOrderByPaymentId(paymentLinkId);

    boolean paymentSuccess = paymentService.ProceedPaymentOrder(
        paymentOrderEntity,
        paymentId,
        paymentLinkId
    );

    if (paymentSuccess) {
      for (Order order : paymentOrderEntity.getOrders()) {
        transactionService.createTransaction(order);
        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport report = sellerReportService.getSellerReport(seller);
        report.setTotalOrders(report.getTotalOrders() + 1);
        report.setTotalSales(report.getTotalSales() + order.getOrderItemList().size());
        sellerReportService.updateSellerReport(report);
      }
    }

    ApiResponse res = new ApiResponse();
    res.setMessage("Payment successful");

    return new ResponseEntity<>(res, HttpStatus.CREATED);

  }


}
