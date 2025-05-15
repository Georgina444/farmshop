package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {
}
