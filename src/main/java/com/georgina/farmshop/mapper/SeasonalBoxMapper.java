// src/main/java/com/georgina/farmshop/mapper/SeasonalBoxMapper.java
package com.georgina.farmshop.mapper;

import com.georgina.farmshop.dto.SeasonalBoxModel;
import com.georgina.farmshop.model.SeasonalBoxEntity;
import org.springframework.stereotype.Component;

@Component
public class SeasonalBoxMapper {

  public SeasonalBoxModel toModel(SeasonalBoxEntity e) {
    return new SeasonalBoxModel(
        e.getId(),
        e.getName(),
        e.getDescription(),
        e.getPrice(),
        e.getImageUrl()
    );
  }

  public SeasonalBoxEntity toEntity(SeasonalBoxModel m) {
    SeasonalBoxEntity e = new SeasonalBoxEntity();
    e.setId(m.getId());
    e.setName(m.getName());
    e.setDescription(m.getDescription());
    e.setPrice(m.getPrice());
    e.setImageUrl(m.getImageUrl());
    return e;
  }
}
