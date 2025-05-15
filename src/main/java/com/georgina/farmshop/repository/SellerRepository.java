package com.georgina.farmshop.repository;


import com.georgina.farmshop.domain.AccountStatus;
import com.georgina.farmshop.model.Seller;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SellerRepository extends JpaRepository<Seller,Long> {

    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
