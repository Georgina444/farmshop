package com.georgina.farmshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // reflects changes made in the cart
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalSellingPrice;

    private int numberOfItems;

    // before discounts or coupons
    // e.g. you saved 20$ (totalMrp - totalSellingPrice)
    private double totalMrpPrice;

//    @Column(name = "total_price")
//    private double totalPrice;

    private int discount;

    // if the user has any coupon applied
    private String couponCode;
}
