package com.georgina.farmshop.service;

import com.georgina.farmshop.dto.SeasonalBoxModel;
import com.georgina.farmshop.model.CartItem;
import com.georgina.farmshop.model.User;
import java.util.List;

public interface SeasonalBoxService {

  List<SeasonalBoxModel> getAllBoxes();

  SeasonalBoxModel getBoxById(Long id) throws Exception;

  SeasonalBoxModel createBox(SeasonalBoxModel m);

  SeasonalBoxModel updateBox(SeasonalBoxModel m, Long id) throws Exception;

  void deleteBox(Long id) throws Exception;

  // business logic for “add box to cart”
  CartItem addBoxToCart(User user, Long boxId, int quantity) throws Exception;
}