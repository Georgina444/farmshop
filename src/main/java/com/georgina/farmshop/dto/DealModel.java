package com.georgina.farmshop.dto;

import lombok.*;

@Data       // само getters/setters, equals/hashCode, toString
@NoArgsConstructor
@AllArgsConstructor
public class DealModel {
  private Long id;
  private Integer discount;
  private Long categoryId;  // само ID на категорията, без пълен обект
}
