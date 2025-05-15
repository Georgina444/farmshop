package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.domain.HomeCategorySection;
import com.georgina.farmshop.model.Deal;
import com.georgina.farmshop.model.HomeCategory;
import com.georgina.farmshop.model.HomePage;
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
    public HomePage createHomePageData(List<HomeCategory> allCategories) {

        List<HomeCategory> gridCategories = allCategories.stream()
                .filter(homeCategory ->
                        homeCategory.getSection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(homeCategory ->
                        homeCategory.getSection()==HomeCategorySection.SHOP_BY_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> boxesCategories = allCategories.stream()
                .filter(homeCategory ->
                        homeCategory.getSection()==HomeCategorySection.BOXES)
                .collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(homeCategory -> homeCategory.getSection()==HomeCategorySection.DEALS)
                .toList();

        List<Deal> createdDeals = new ArrayList<>();

        if(dealRepository.findAll().isEmpty()){
            List<Deal> deals = allCategories.stream()
                    .filter(homeCategory -> homeCategory.getSection()==HomeCategorySection.DEALS)
                    .map(homeCategory -> new Deal(null,10,homeCategory))
                    .collect(Collectors.toList());
            createdDeals = dealRepository.saveAll(deals);
        }else createdDeals = dealRepository.findAll();

        HomePage homePage = new HomePage();
        homePage.setGrid(gridCategories);
        homePage.setShopByCategories(shopByCategories);
        homePage.setBoxes(boxesCategories);
        homePage.setDeals(createdDeals);

        return homePage;
    }
}
