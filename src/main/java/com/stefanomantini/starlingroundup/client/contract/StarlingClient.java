package com.stefanomantini.starlingroundup.client.contract;

import com.stefanomantini.starlingroundup.client.dto.AccountWrapper;
import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface StarlingClient {
  /**
   * Get account details using a bearer token
   *
   * @return responseEntity of Account[]
   */
  ResponseEntity<AccountWrapper> GetAccountDetails();

  /**
   * Get feed details using a bearer token
   *
   * @param accountId the id of the account
   * @param categoryId the id of the category
   * @param changesSince the timestamp of when to retrieve from
   * @return responseEntity of FeedItemWrapper
   */
  ResponseEntity<FeedItemWrapper> GetFeedForAccount(
      UUID accountId, UUID categoryId, LocalDateTime changesSince);
}
