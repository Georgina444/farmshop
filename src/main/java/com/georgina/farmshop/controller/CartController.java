package com.georgina.farmshop.controller;

import com.georgina.farmshop.exceptions.ProductException;
import com.georgina.farmshop.model.Cart;
import com.georgina.farmshop.model.CartItem;
import com.georgina.farmshop.model.Product;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.model.request.AddItemRequest;
import com.georgina.farmshop.model.response.ApiResponse;
import com.georgina.farmshop.service.CartItemService;
import com.georgina.farmshop.service.CartService;
import com.georgina.farmshop.service.ProductService;
import com.georgina.farmshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

  private final CartService cartService;
  private final CartItemService cartItemService;
  private final UserService userService;
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<Cart> findUserCartHandler(
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartService.findUserCart(user);

    return new ResponseEntity<Cart>(cart, HttpStatus.OK);
  }

  @PutMapping("/add")
  public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
      @RequestHeader("Authorization") String jwt)
      throws ProductException, Exception {

    User user = userService.findUserByJwtToken(jwt);
    Product product = productService.findProductById(req.getProductId());

    CartItem item = cartService.addCartItem(user,
        product,
        req.getSize(),
        req.getQuantity());

    ApiResponse res = new ApiResponse();
    res.setMessage("Item added to cart successfully.");

    return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
  }

  @DeleteMapping("/item/{cartItemId}")
  public ResponseEntity<ApiResponse> deleteCartItemHandler(
      @PathVariable Long cartItemId,
      @RequestHeader("Authorization") String jwt)
      throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    cartItemService.removeCartItem(user.getId(), cartItemId);

    ApiResponse res = new ApiResponse();
    res.setMessage("Item removed from cart successfully");

    return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
  }

  @PutMapping("/item/{cartItemId}")
  public ResponseEntity<CartItem> updateCartItemHandler(
      @PathVariable Long cartItemId,
      @RequestBody CartItem cartItem,
      @RequestHeader("Authorization") String jwt)
      throws Exception {

    User user = userService.findUserByJwtToken(jwt);

    CartItem updatedCartItem = null;
    if (cartItem.getQuantity() > 0) {
      updatedCartItem = cartItemService.updateCartItem(user.getId(),
          cartItemId, cartItem);
    }

    return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);
  }


}
