package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.model.Cart;
import com.georgina.farmshop.model.Coupon;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.repository.CartRepository;
import com.georgina.farmshop.repository.CouponRepository;
import com.georgina.farmshop.repository.UserRepository;
import com.georgina.farmshop.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {


    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    @Override
    public Cart applyCoupon(String code, double orderValue, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(code);
        Cart cart = cartRepository.findByUserId(user.getId());

        if (coupon == null) {
            throw new Exception("Coupon not found.");
        }
        if (user.getUsedCoupons().contains(coupon)) {
            throw new Exception("This coupon has been used already. You can only use it once!");
        }
        if (orderValue < coupon.getMinimumOrderValue()) {
            throw new Exception("You can only use this coupon for an order greater than " + coupon.getMinimumOrderValue());
        }
        if (coupon.isActive() &&
                LocalDate.now().isAfter(coupon.getValidityStartDate())
                && LocalDate.now().isBefore(coupon.getValidityEndDate())
        ){
            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double discountedPrice = (cart.getTotalSellingPrice()*coupon.getDiscountPercentage())/100;

            cart.setTotalSellingPrice(cart.getTotalSellingPrice()-discountedPrice);
            cart.setCouponCode(code);
            cartRepository.save(cart);
            return cart;
        }
        throw new Exception("Invalid coupon.");
    }

    @Override
    public Cart removeCoupon(String code, User user) throws Exception {
        Coupon coupon = couponRepository.findByCode(code);

        if(coupon==null){
            throw new Exception("Coupon not found.");
        }

        Cart cart = cartRepository.findByUserId(user.getId());
        double discountedPrice = (cart.getTotalSellingPrice()*coupon.getDiscountPercentage())/100;

        cart.setTotalSellingPrice(cart.getTotalSellingPrice()+discountedPrice);
        cart.setCouponCode(null);

        return cartRepository.save(cart);
    }

    @Override
    public Coupon findCouponById(Long id) throws Exception {

        return couponRepository.findById(id).orElseThrow(()->
                 new Exception("Coupon not found."));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCoupon(Long id) throws Exception {
        findCouponById(id);
        couponRepository.deleteById(id);
    }
}
