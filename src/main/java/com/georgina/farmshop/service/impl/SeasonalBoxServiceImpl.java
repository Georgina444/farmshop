// src/main/java/com/georgina/farmshop/service/impl/SeasonalBoxServiceImpl.java
package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.dto.SeasonalBoxModel;
import com.georgina.farmshop.mapper.SeasonalBoxMapper;
import com.georgina.farmshop.model.*;
import com.georgina.farmshop.repository.SeasonalBoxRepository;
import com.georgina.farmshop.service.CartService;
import com.georgina.farmshop.service.SeasonalBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeasonalBoxServiceImpl implements SeasonalBoxService {

  private final SeasonalBoxRepository boxRepo;
  private final SeasonalBoxMapper mapper;
  private final CartService cartService;

  @Override
  public List<SeasonalBoxModel> getAllBoxes() {
    return boxRepo.findAll().stream()
        .map(mapper::toModel)
        .collect(Collectors.toList());
  }

  @Override
  public SeasonalBoxModel getBoxById(Long id) throws Exception {
    SeasonalBoxEntity e = boxRepo.findById(id)
        .orElseThrow(() -> new Exception("Box not found"));
    return mapper.toModel(e);
  }

  @Override
  public SeasonalBoxModel createBox(SeasonalBoxModel m) {
    SeasonalBoxEntity e = mapper.toEntity(m);
    return mapper.toModel(boxRepo.save(e));
  }

  @Override
  public SeasonalBoxModel updateBox(SeasonalBoxModel m, Long id) throws Exception {
    SeasonalBoxEntity exist = boxRepo.findById(id)
        .orElseThrow(() -> new Exception("Box not found"));
    // only mutable fields
    exist.setName(m.getName());
    exist.setDescription(m.getDescription());
    exist.setPrice(m.getPrice());
    exist.setImageUrl(m.getImageUrl());
    return mapper.toModel(boxRepo.save(exist));
  }

  @Override
  public void deleteBox(Long id) throws Exception {
    SeasonalBoxEntity e = boxRepo.findById(id)
        .orElseThrow(() -> new Exception("Box not found"));
    boxRepo.delete(e);
  }

  @Override
  public CartItem addBoxToCart(User user, Long boxId, int quantity) throws Exception {
    SeasonalBoxEntity box = boxRepo.findById(boxId)
        .orElseThrow(() -> new Exception("Box not found"));
    // wrap box as a “fake” Product for cart mechanics
    Product p = new Product();
    p.setId(box.getId());
    p.setProductName(box.getName());
    p.setSellingPrice(box.getPrice().intValue());
    // reuse your existing cartService
    return cartService.addCartItem(user, p, "BOX", quantity);
  }
}
