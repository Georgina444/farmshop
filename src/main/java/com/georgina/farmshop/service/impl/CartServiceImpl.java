package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.model.Cart;
import com.georgina.farmshop.model.CartItem;
import com.georgina.farmshop.model.Product;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.repository.CartItemRepository;
import com.georgina.farmshop.repository.CartRepository;
import com.georgina.farmshop.service.CartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;

  @Override
  public CartItem addCartItem(User user, Product product, String size, int quantity) {

    Cart cart = findUserCart(user);

    Optional<CartItem> cartItemOpt =
        cartItemRepository.findByCartAndProductAndSize(cart, product, size);

    if (cartItemOpt.isPresent()) {
      return cartItemOpt.get();
    }

    CartItem cartItem = new CartItem();
    cartItem.setProduct(product);
    cartItem.setQuantity(quantity);
    cartItem.setUserId(user.getId());
    cartItem.setSize(size);

    int totalPrice = quantity * product.getSellingPrice();
    cartItem.setSellingPrice(totalPrice);
    cartItem.setMrpPrice(quantity * product.getMrpPrice());

    cart.getCartItems().add(cartItem);
    cartItem.setCart(cart);

    return cartItemRepository.save(cartItem);
  }

  @Override
  public Cart findUserCart(User user) {
    Cart cart = cartRepository.findByUserId(user.getId());
    if (cart == null) {
      cart = new Cart();
      cart.setUser(user);
      cart = cartRepository.save(cart);
    }

    int totalMrpPrice = 0;
    int totalPrice = 0;   // sum of all item.sellingPrice
    int totalItems = 0;

    for (CartItem item : cart.getCartItems()) {
      // avoid NPE if mrpPrice was ever null
      totalMrpPrice += (item.getMrpPrice() != null
          ? item.getMrpPrice()
          : 0);
      totalPrice += item.getSellingPrice();
      totalItems += item.getQuantity();
    }

    cart.setTotalMrpPrice(totalMrpPrice);
    cart.setTotalSellingPrice(totalPrice);
    cart.setNumberOfItems(totalItems);
    cart.setDiscount(calculateDiscountPercentage(
        totalMrpPrice, totalPrice));

    // persist updated totals
    return cartRepository.save(cart);
  }


  private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
    if (mrpPrice <= 0) {
      //throw new IllegalArgumentException("Actual price must be greater than 0");
      return 0;
    }
    double discount = mrpPrice - sellingPrice;
    double discountPercentage = (discount / mrpPrice) * 100;
    return (int) discountPercentage;
  }
}
