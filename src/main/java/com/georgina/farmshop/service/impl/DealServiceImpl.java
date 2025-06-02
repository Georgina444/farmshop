package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.dto.DealModel;
import com.georgina.farmshop.model.DealEntity;
import com.georgina.farmshop.model.HomeCategory;
import com.georgina.farmshop.repository.DealRepository;
import com.georgina.farmshop.repository.HomeCategoryRepository;
import com.georgina.farmshop.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

  private final DealRepository dealRepository;
  private final HomeCategoryRepository homeCategoryRepository;

  /** 1) Fetch all entities → map to DTOs */
  @Override
  public List<DealModel> getDeals() {
    return dealRepository.findAll().stream()
        .map(this::toModel)
        .collect(Collectors.toList());
  }

  /** 2) Accept a DTO, map to entity, save, map back to DTO */
  @Override
  public DealModel createDeal(DealModel model) {
    DealEntity entity = toEntity(model);
    DealEntity saved = dealRepository.save(entity);
    return toModel(saved);
  }

  /** 3) Update by id using DTO fields */
  @Override
  public DealModel updateDeal(DealModel model, Long id) throws Exception {
    DealEntity existing = dealRepository.findById(id)
        .orElseThrow(() -> new Exception("Deal not found."));
    if (model.getDiscount() != null) {
      existing.setDiscount(model.getDiscount());
    }
    if (model.getCategoryId() != null) {
      HomeCategory cat = homeCategoryRepository.findById(model.getCategoryId())
          .orElseThrow(() -> new Exception("Category not found."));
      existing.setCategory(cat);
    }
    DealEntity updated = dealRepository.save(existing);
    return toModel(updated);
  }

  /** 4) Simple delete */
  @Override
  public void deleteDeal(Long id) throws Exception {
    DealEntity e = dealRepository.findById(id)
        .orElseThrow(() -> new Exception("Deal not found."));
    dealRepository.delete(e);
  }

  // --- helper to convert Entity → DTO ---
  private DealModel toModel(DealEntity e) {
    return new DealModel(
        e.getId(),
        e.getDiscount(),
        e.getCategory().getId()
    );
  }

  // --- helper to convert DTO → Entity ---
  private DealEntity toEntity(DealModel m) {
    DealEntity e = new DealEntity();
    if (m.getId() != null) {
      e.setId(m.getId());
    }
    e.setDiscount(m.getDiscount());
    if (m.getCategoryId() != null) {
      homeCategoryRepository.findById(m.getCategoryId())
          .ifPresent(e::setCategory);
    }
    return e;
  }
}
