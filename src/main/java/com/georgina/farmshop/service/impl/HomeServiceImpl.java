package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.domain.HomeCategorySection;
import com.georgina.farmshop.model.DealEntity;
import com.georgina.farmshop.model.HomeCategory;
import com.georgina.farmshop.model.HomePageEntity;
import com.georgina.farmshop.repository.DealRepository;
import com.georgina.farmshop.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

  private final DealRepository dealRepository;


  @Override
  public HomePageEntity createHomePageData(List<HomeCategory> allCategories) {

    List<HomeCategory> gridCategories = allCategories.stream()
        .filter(homeCategory ->
            homeCategory.getSection() == HomeCategorySection.GRID)
        .collect(Collectors.toList());

    List<HomeCategory> shopByCategories = allCategories.stream()
        .filter(homeCategory ->
            homeCategory.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES)
        .collect(Collectors.toList());

    List<HomeCategory> boxesCategories = allCategories.stream()
        .filter(homeCategory ->
            homeCategory.getSection() == HomeCategorySection.BOXES)
        .collect(Collectors.toList());

    List<HomeCategory> dealCategories = allCategories.stream()
        .filter(homeCategory -> homeCategory.getSection() == HomeCategorySection.DEALS)
        .toList();

    List<DealEntity> createdDealEntities = new ArrayList<>();

      if (dealRepository.findAll().isEmpty()) {
          List<DealEntity> dealEntities = allCategories.stream()
              .filter(homeCategory -> homeCategory.getSection() == HomeCategorySection.DEALS)
              .map(homeCategory -> new DealEntity(null, 10, homeCategory))
              .collect(Collectors.toList());
          createdDealEntities = dealRepository.saveAll(dealEntities);
      } else {
          createdDealEntities = dealRepository.findAll();
      }

    HomePageEntity homePageEntity = new HomePageEntity();
    homePageEntity.setGrid(gridCategories);
    homePageEntity.setShopByCategories(shopByCategories);
    homePageEntity.setBoxes(boxesCategories);
    homePageEntity.setDealEntities(createdDealEntities);

    return homePageEntity;
  }
}
