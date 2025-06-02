package com.georgina.farmshop.service;

import com.georgina.farmshop.dto.DealModel;
import com.georgina.farmshop.model.DealEntity;

import java.util.List;

public interface DealService {

  List<DealModel> getDeals();

  // Admin can:
  DealModel createDeal(DealModel dealModel);

  DealModel updateDeal(DealModel dealModel, Long id) throws Exception;

  void deleteDeal(Long id) throws Exception;
}
