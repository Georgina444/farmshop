package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

  Wishlist findByUserId(Long userId);
}
