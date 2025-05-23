package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Deal;

import java.util.List;

public interface DealService {

  List<Deal> getDeals();

  // Admin can:
  Deal createDeal(Deal deal);

  Deal updateDeal(Deal deal, Long id) throws Exception;

  void deleteDeal(Long id) throws Exception;
}
