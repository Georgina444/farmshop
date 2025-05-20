package com.georgina.farmshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.georgina.farmshop.domain.PaymentOrderStatus;
import com.georgina.farmshop.domain.UserRoles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // 1:1 cart → delete cart when user is deleted
  @OneToOne(mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Cart cart;

  // 1:N orders → delete orders + their OrderItems
  @OneToMany(mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  // 1:N reviews
  @OneToMany(mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<Review> reviews = new HashSet<>();

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  private String email;
  private String fullName;
  private String phoneNumber;
  private UserRoles role = UserRoles.ROLE_CUSTOMER;

  // have the address saved on their profile
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Address> addresses = new HashSet<>();

  // use the coupon ONLY ONCE
// N:N coupons used – you usually want to just clear the join-table
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "user_used_coupons",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "coupon_id"))
  private Set<Coupon> usedCoupons = new HashSet<>();

  // 1:1 wishlist → delete the wishlist row when the user is deleted
  @OneToOne(mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Wishlist wishlist;

  // 1:N payment orders → delete all payment orders
  @OneToMany(mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<PaymentOrderEntity> paymentOrders = new ArrayList<>();

  // 1:N transactions → delete all transactions
  @OneToMany(mappedBy = "customer",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Transaction> transactions = new ArrayList<>();


}
