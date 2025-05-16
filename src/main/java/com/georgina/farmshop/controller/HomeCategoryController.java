package com.georgina.farmshop.controller;


import com.georgina.farmshop.model.HomeCategory;
import com.georgina.farmshop.model.HomePage;
import com.georgina.farmshop.service.HomeCategoryService;
import com.georgina.farmshop.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeCategoryController {

    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @PostMapping("/home/categories")
    public ResponseEntity<HomePage> createHomeCategories(
            @RequestBody List<HomeCategory> homeCategories
            ) {
        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
        HomePage homePage = homeService.createHomePageData(categories);
        return new ResponseEntity<>(homePage, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategory() throws Exception{
        List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(
            @PathVariable Long id,
            @RequestBody HomeCategory homeCategory) throws Exception{

        HomeCategory updatedHomeCategory = homeCategoryService.updateHomeCategory(homeCategory, id);
        return ResponseEntity.ok(updatedHomeCategory);

    }



}
