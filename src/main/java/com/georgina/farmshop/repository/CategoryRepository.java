package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Category findByCategoryId(String categoryId);
}
