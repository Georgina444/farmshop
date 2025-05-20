package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.SellerReport;

public interface SellerReportService {


  SellerReport getSellerReport(Seller seller);

  SellerReport updateSellerReport(SellerReport sellerReport);
}
