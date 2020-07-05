package com.stefanomantini.starlingroundup.client.contract;

import com.stefanomantini.starlingroundup.client.dto.Account;
import org.springframework.http.ResponseEntity;

public interface StarlingClient {
  ResponseEntity<Account[]> GetAccountDetails();
}
