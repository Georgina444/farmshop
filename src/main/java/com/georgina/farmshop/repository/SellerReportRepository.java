package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {

  SellerReport findBySellerId(Long sellerId);
}
