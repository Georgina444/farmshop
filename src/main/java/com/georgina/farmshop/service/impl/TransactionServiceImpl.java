package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.model.Order;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.Transaction;
import com.georgina.farmshop.repository.SellerRepository;
import com.georgina.farmshop.repository.TransactionRepository;
import com.georgina.farmshop.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;


    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }
}
