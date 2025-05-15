package com.georgina.farmshop.controller;


import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.Transaction;
import com.georgina.farmshop.service.SellerService;
import com.georgina.farmshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final SellerService sellerService;

    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getTransactionBySellerId(
            @RequestHeader("Authorization")String jwt) throws Exception{

        Seller seller = sellerService.getSellerProfile(jwt);

        List<Transaction> transactions = transactionService.getTransactionsBySellerId(seller);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = transactionService.getAllTransaction();
        return ResponseEntity.ok(transactions);
    }

}
