package com.georgina.farmshop.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;


// why is it not an entity?
@Data
public class HomePage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private List<HomeCategory> grid;
    private List<HomeCategory> shopByCategories;
    private List<HomeCategory> boxes;
    private List<HomeCategory> dealCategories;
    private List<Deal> deals;




}
