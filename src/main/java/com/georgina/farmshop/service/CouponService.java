package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Cart;
import com.georgina.farmshop.model.Coupon;
import com.georgina.farmshop.model.User;

import java.util.List;

public interface CouponService {

    Cart applyCoupon(String code, double orderValue, User user) throws Exception;
    Cart removeCoupon(String code, User user) throws Exception;
    Coupon findCouponById(Long id) throws Exception;
    Coupon createCoupon(Coupon coupon);
    List<Coupon> findAllCoupons();
    void deleteCoupon(Long id) throws Exception;
}
