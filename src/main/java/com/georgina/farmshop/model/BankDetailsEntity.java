package com.georgina.farmshop.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BankDetailsEntity {

  private String accountNumber;
  private String accountHolderName;
  private String ifscCode;
}
