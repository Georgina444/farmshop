package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Product;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.model.Wishlist;

public interface WishlistService {

  Wishlist createWishlist(User user);

  Wishlist getWishlistByUserId(User user);

  Wishlist addProductToWishlist(User user, Product product);
}
