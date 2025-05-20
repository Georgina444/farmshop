package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Cart;
import com.georgina.farmshop.model.CartItem;
import com.georgina.farmshop.model.Product;
import com.georgina.farmshop.model.User;

public interface CartService {

  public CartItem addCartItem(
      User user,
      Product product,
      String size,
      int quantity
  );

  public Cart findUserCart(User user);
}
