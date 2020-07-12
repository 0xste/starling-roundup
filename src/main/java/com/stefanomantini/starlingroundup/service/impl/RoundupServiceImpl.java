package com.stefanomantini.starlingroundup.service.impl;

import com.stefanomantini.starlingroundup.client.contract.StarlingClient;
import com.stefanomantini.starlingroundup.client.dto.*;
import com.stefanomantini.starlingroundup.client.exception.RemoteGatewayException;
import com.stefanomantini.starlingroundup.service.contract.RoundupService;
import com.stefanomantini.starlingroundup.service.exception.BusinessException;
import com.stefanomantini.starlingroundup.service.exception.TechnicalException;
import com.stefanomantini.starlingroundup.service.model.RoundupSummary;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoundupServiceImpl implements RoundupService {

  private final Currency gbp = Currency.getInstance("GBP");

  @Autowired StarlingClient starlingClient;

  /**
   * assuming the customer has at least 1 account, returning the first one back from the api for
   * brevity
   *
   * @return
   * @throws TechnicalException
   * @throws BusinessException
   */
  private List<AccountDTO> HandleAccount() throws TechnicalException, BusinessException {
    final List<AccountDTO> accountResponse;
    try {
      accountResponse = starlingClient.GetAccountDetails().getAccounts();
    } catch (final RemoteGatewayException e) {
      throw new TechnicalException(
          "Internal Server Error, upstream service failure" + e.getMessage());
    }
    if (accountResponse.size() == 0) {
      throw new BusinessException("No accounts found for customer");
    }
    if (accountResponse.size() > 1) {
      log.warn("multiple accounts found for customer, using first returned");
    }
    return accountResponse;
  }

  /**
   * lists a customers transactions and handles mappings
   *
   * @param customerAccounts
   * @param from
   * @return
   * @throws TechnicalException
   */
  private List<FeedItemDTO> HandleFeed(
      final List<AccountDTO> customerAccounts, final ZonedDateTime from) throws TechnicalException {
    final List<FeedItemDTO> feed;
    try {
      final Optional<AccountDTO> customerAccount = Optional.ofNullable(customerAccounts.get(0));
      feed =
          starlingClient
              .GetFeedForAccount(
                  customerAccount.get().accountUid,
                  customerAccount.get().getDefaultCategory(),
                  from)
              .getFeedItems();
    } catch (final RemoteGatewayException e) {
      throw new TechnicalException(
          "Internal Server Error, upstream service failure" + e.getLocalizedMessage());
    }
    return feed;
  }

  // todo integrate this for savings goals that don't exist
  private CreateSavingsGoalResponseDTO handleSavingsGoalCreation(
      final UUID accountId, final ZonedDateTime from, final ZonedDateTime to)
      throws TechnicalException, BusinessException {
    final CreateSavingsGoalResponseDTO createSavingsGoalResponseDTO;
    try {
      createSavingsGoalResponseDTO =
          starlingClient.CreateNewSavingsGoal(
              accountId,
              "Roundup from: "
                  + from.format(DateTimeFormatter.ISO_LOCAL_DATE)
                  + " - "
                  + to.format(DateTimeFormatter.ISO_LOCAL_DATE),
              AmountDTO.builder().currency(gbp).minorUnits(1000).build());
    } catch (final RemoteGatewayException e) {
      throw new TechnicalException(
          "Internal Server Error, upstream service failure" + e.getLocalizedMessage());
    }
    if (!createSavingsGoalResponseDTO.getSuccess()) {
      throw new BusinessException(
          "Failed to add to savings pot" + createSavingsGoalResponseDTO.getErrors().toString());
    }
    return createSavingsGoalResponseDTO;
  }

  @Override
  public RoundupSummary roundUpForPeriod(
      final UUID accountId,
      final UUID savingsGoalId,
      final ZonedDateTime from,
      final ZonedDateTime to)
      throws BusinessException, TechnicalException {

    final List<AccountDTO> customerAccounts = HandleAccount();

    assert (customerAccounts.get(0).accountUid.equals(accountId));

    final List<AmountDTO> amountsToRound = new ArrayList<>();
    for (final FeedItemDTO feedItem : HandleFeed(customerAccounts, from)) {
      if (feedItem.getTransactionTime().isAfter(from)
          && feedItem.getTransactionTime().isBefore(to)) {
        amountsToRound.add(feedItem.getAmount());
      }
    }

    final AmountDTO roundupAmount =
        AmountDTO.builder().currency(gbp).minorUnits(calculateTotalRoundup(amountsToRound)).build();

    if (roundupAmount.getMinorUnits() < 1) {
      return RoundupSummary.builder()
          .savingsGoalUid(new UUID(0, 0))
          .accountId(accountId)
          .transferUid(new UUID(0, 0))
          .totalAdded(AmountDTO.builder().currency(gbp).minorUnits(0).build())
          .build();
    }
    final UUID transferUid = handleAddMoney(accountId, savingsGoalId, roundupAmount);

    log.info(
        String.format(
            "rounded up %d from #%d payments from %s to %s for account %s into goal %s",
            roundupAmount.getMinorUnits(),
            amountsToRound.size(),
            from.toString(),
            to.toString(),
            accountId.toString(),
            savingsGoalId.toString()));

    return RoundupSummary.builder()
        .savingsGoalUid(savingsGoalId)
        .accountId(accountId)
        .transferUid(transferUid)
        .totalAdded(roundupAmount)
        .build();
  }

  private UUID handleAddMoney(
      final UUID accountId, final UUID savingsGoalId, final AmountDTO roundupAmount)
      throws BusinessException {
    try {
      final AddMoneyToSavingsGoalResponseDTO addMoney =
          starlingClient.AddMoneyToSavingsGoal(accountId, savingsGoalId, roundupAmount);
      return addMoney.getTransferUid();
    } catch (final BusinessException | RemoteGatewayException | TechnicalException e) {
      throw new BusinessException(e.getLocalizedMessage());
    }
  }

  @Override
  public Integer calculateTotalRoundup(final List<AmountDTO> amounts) {
    return amounts.stream().collect(Collectors.summingInt(o -> o.getRoundup(1)));
  }
}
