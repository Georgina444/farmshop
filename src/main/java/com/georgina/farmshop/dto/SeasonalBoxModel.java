package com.georgina.farmshop.dto;

import com.georgina.farmshop.domain.Season;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonalBoxModel {


    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Season season;
    private List<Long> productIds;

    public SeasonalBoxModel(Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl) {
        this.id          = id;
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.imageUrl    = imageUrl;
    }

}
