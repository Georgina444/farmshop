package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.Cart;
import com.georgina.farmshop.model.CartItem;
import com.georgina.farmshop.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  Optional<CartItem> findByCartAndProductAndSize(Cart cart, Product product, String size);

}
