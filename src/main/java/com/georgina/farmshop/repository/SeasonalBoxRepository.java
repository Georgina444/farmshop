package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.SeasonalBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonalBoxRepository
    extends JpaRepository<SeasonalBoxEntity, Long> { }