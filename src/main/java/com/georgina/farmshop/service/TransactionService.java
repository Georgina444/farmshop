package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.Transaction;
import com.georgina.farmshop.model.Order;

import java.util.List;

//Manages transactions between users, orders, and sellers
public interface TransactionService {

  Transaction createTransaction(Order order);

  List<Transaction> getTransactionsBySellerId(Seller seller);

  List<Transaction> getAllTransaction();

}
