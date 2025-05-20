package com.georgina.farmshop.domain;

public enum AccountStatus {

  PENDING_VERIFICATION,
  ACTIVE,
  SUSPENDED, // due to violations
  DEACTIVATED,
  BANNED,
  CLOSED // user has chosen to close
}
