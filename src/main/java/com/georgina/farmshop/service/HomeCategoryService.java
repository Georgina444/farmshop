package com.georgina.farmshop.service;

import com.georgina.farmshop.model.HomeCategory;

import java.util.List;

public interface HomeCategoryService {

  HomeCategory createHomeCategory(HomeCategory homeCategory);

  List<HomeCategory> createCategories(List<HomeCategory> homeCategories);

  HomeCategory updateHomeCategory(HomeCategory homeCategory, Long id) throws Exception;

  List<HomeCategory> getAllHomeCategories();
}
