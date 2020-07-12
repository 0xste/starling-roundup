package com.stefanomantini.starlingroundup.client.contract;

import com.stefanomantini.starlingroundup.client.dto.*;
import com.stefanomantini.starlingroundup.client.exception.RemoteGatewayException;
import com.stefanomantini.starlingroundup.service.exception.BusinessException;
import com.stefanomantini.starlingroundup.service.exception.TechnicalException;
import java.time.ZonedDateTime;
import java.util.UUID;

public interface StarlingClient {
  /**
   * Get account details using a bearer token
   *
   * @return responseEntity of Account[]
   */
  AccountWrapperDTO GetAccountDetails() throws RemoteGatewayException;

  /**
   * Get feed details using a bearer token
   *
   * @param accountId the id of the account
   * @param categoryId the id of the category
   * @param changesSince the timestamp of when to retrieve from
   * @return responseEntity of FeedItemWrapper
   */
  FeedItemWrapperDTO GetFeedForAccount(UUID accountId, UUID categoryId, ZonedDateTime changesSince)
      throws RemoteGatewayException;

  /**
   * Create New Savings Goal
   *
   * @param accountId
   * @param name
   * @param target
   * @return
   */
  CreateSavingsGoalResponseDTO CreateNewSavingsGoal(UUID accountId, String name, AmountDTO target)
      throws RemoteGatewayException;

  /**
   * Add money to Savings Goal
   *
   * @param accountId
   * @param savingsGoalId
   * @param toAdd
   * @return
   */
  AddMoneyToSavingsGoalResponseDTO AddMoneyToSavingsGoal(
      UUID accountId, UUID savingsGoalId, AmountDTO toAdd)
      throws RemoteGatewayException, BusinessException, TechnicalException;
}
