package com.stefanomantini.starlingroundup.client.contract;

import com.stefanomantini.starlingroundup.client.dto.AccountWrapper;
import com.stefanomantini.starlingroundup.client.dto.Amount;
import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import com.stefanomantini.starlingroundup.client.dto.SavingsGoalRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

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

  /**
   * Create New Savings Goal
   *
   * @param accountId
   * @param name
   * @param target
   * @return
   */
  ResponseEntity<SavingsGoalRequest> CreateNewSavingsGoal(
      UUID accountId, String name, Amount target);
}
