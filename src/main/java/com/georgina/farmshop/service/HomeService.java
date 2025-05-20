package com.georgina.farmshop.service;

import com.georgina.farmshop.model.HomeCategory;
import com.georgina.farmshop.model.HomePageEntity;

import java.util.List;

public interface HomeService {

  public HomePageEntity createHomePageData(List<HomeCategory> allCategories);
}
