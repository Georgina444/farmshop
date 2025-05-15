package com.georgina.farmshop.model;

import com.georgina.farmshop.domain.AccountStatus;
import com.georgina.farmshop.domain.USER_ROLES;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sellerName;

    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    // for pure value object you never share or query on their own(like bank details - lives and dies inside a Seller)
    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickupAddress = new Address();

    private String GSTIN; //??

    private USER_ROLES role = USER_ROLES.ROLE_SELLER;
    private boolean isEmailVerified=false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

}
