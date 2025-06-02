package com.georgina.farmshop.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BusinessDetails {

  private String businessName;
  private String businessEmail;
  ;
  private String businessMobile;
  private String businessAddress;
  private String businessLogo;
  private String banner;
}

// Why is this class not an entity?

//The BusinessDetails class is an embedded object within the Seller entity, indicated by the use of @Embedded.
// This means it's meant to be a part of the Seller entity and does not need its own table in the database.
// It represents business-related data (e.g., name, email, logo) that is stored within a seller's profile.
// Since it is embedded within Seller, it doesn’t require a separate table but should be a part of the Seller table.