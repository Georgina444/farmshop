package com.georgina.farmshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsEntity {

  private String accountNumber;
  private String accountHolderName;
  private String ifscCode;
}
