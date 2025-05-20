package com.georgina.farmshop.controller;

import com.georgina.farmshop.model.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


  @GetMapping("/")
  public ApiResponse getWelcomeMessage() {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("Welcome to our online farm shop");
    return apiResponse;
  }
}
