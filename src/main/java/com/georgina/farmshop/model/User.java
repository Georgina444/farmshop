package com.georgina.farmshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.georgina.farmshop.domain.USER_ROLES;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;
    private String fullName;
    private String phoneNumber;
    private USER_ROLES role = USER_ROLES.ROLE_CUSTOMER;

    // have the address saved on their profile
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    // use the coupon ONLY ONCE
    @ManyToMany
    @JsonIgnore   // data will not be in the front end
    private Set<Coupon> usedCoupons = new HashSet<>();

}
