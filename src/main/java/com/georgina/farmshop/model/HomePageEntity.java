package com.georgina.farmshop.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

// Why is it not an entity?
// HomePage is more of a DTO that aggregates other entities like HomeCategory and Deal to construct the homepage layout.
// This class will not have its own table in the database, but instead, it can be built dynamically by combining data from other entities.
@Data
public class HomePageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private List<HomeCategory> grid;
  private List<HomeCategory> shopByCategories;
  private List<HomeCategory> boxes;
  private List<HomeCategory> dealCategories;
  private List<Deal> deals;


}
