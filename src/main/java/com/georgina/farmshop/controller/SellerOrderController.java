package com.georgina.farmshop.controller;

import com.georgina.farmshop.domain.OrderStatus;
import com.georgina.farmshop.exceptions.SellerException;
import com.georgina.farmshop.model.Order;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.service.OrderService;
import com.georgina.farmshop.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/orders")
public class SellerOrderController {

    private final OrderService orderService;
    private final SellerService sellerService;

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersHandler(
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Order> orders = orderService.sellersOrder(seller.getId());

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);

    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderHandler(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long orderId,
            @PathVariable OrderStatus orderStatus
    ) throws Exception{

        Order order = orderService.updateOrderStatus(orderId,orderStatus);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }


}
