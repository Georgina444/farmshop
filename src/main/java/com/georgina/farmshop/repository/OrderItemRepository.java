package com.georgina.farmshop.repository;

import com.georgina.farmshop.model.Order;
import com.georgina.farmshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
