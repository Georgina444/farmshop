package com.georgina.farmshop.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data  // getters and setters available
public class AddItemRequest {

  @NotNull
  private String size;
  private int quantity;
  private Long productId;
}
