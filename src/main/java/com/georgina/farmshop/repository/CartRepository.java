package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long id);


}
