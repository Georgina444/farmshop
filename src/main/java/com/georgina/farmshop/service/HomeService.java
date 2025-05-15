package com.georgina.farmshop.service;

import com.georgina.farmshop.model.HomeCategory;
import com.georgina.farmshop.model.HomePage;

import java.util.List;

public interface HomeService {

    public HomePage createHomePageData(List<HomeCategory> allCategories);
}
