package com.georgina.farmshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  // the Product class is an entity because it needs to be stored in the database,
  // but it’s also a domain class because it encapsulates business logic related to products.
  // It has fields like price, discountPercentage, name, and methods to calculate the final price based on business rules.

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String productName;

  private String description;

  private int sellingPrice;
  private int mrpPrice;
  private String color;

  private int discountPercentage;

  private int quantity;

  @ElementCollection
  private List<String> images = new ArrayList<>();


  @Transient  // not stored in its own table; just derived from reviews
  private Integer numberOfRating;  // allows for no ratings at all(can be null) | the primitive type int has to have a value.
  @ManyToOne
  private Category category;
  // which seller is this product directed to
  @ManyToOne
  @JsonIgnore
  private Seller seller;
  private LocalDateTime createdAt;
  private String sizes;
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  public int getNumberOfRating() {
    return reviews.size();
  }

}
