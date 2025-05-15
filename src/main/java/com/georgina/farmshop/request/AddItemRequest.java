package com.georgina.farmshop.request;

import lombok.Data;

@Data  // getters and setters available
public class AddItemRequest {

    private String size;
    private int quantity;
    private Long productId;
}
