package com.georgina.farmshop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne  // one cart can have multiple items
  @JsonIgnore
  private Cart cart;

  @ManyToOne
  private Product product;

  private String size;

  private int quantity = 1;

  private Integer mrpPrice;

  private Integer sellingPrice;

  private long userId;
}
