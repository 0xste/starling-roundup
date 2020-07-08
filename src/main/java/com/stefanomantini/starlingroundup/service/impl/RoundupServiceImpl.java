package com.stefanomantini.starlingroundup.service.impl;

import com.stefanomantini.starlingroundup.client.contract.StarlingClient;
import com.stefanomantini.starlingroundup.client.dto.Account;
import com.stefanomantini.starlingroundup.client.dto.AccountWrapper;
import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import com.stefanomantini.starlingroundup.service.contract.RoundupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoundupServiceImpl
    implements RoundupService,
        com.stefanomantini.starlingroundup.service.contract.RoundupServiceImpl {

  @Autowired StarlingClient starlingClient;

  @Override
  public FeedItemWrapper getRoundupAmount() {
    final ResponseEntity<AccountWrapper> accountResponse = starlingClient.GetAccountDetails();
    if (accountResponse.getStatusCode() == HttpStatus.OK) {
      //        assuming there's at least one, we always take the first
      if (accountResponse.getBody().getAccounts().size() > 0) {
        final Account account = accountResponse.getBody().getAccounts().get(0);

        // TODO investigate 404 back from this
        final ResponseEntity<FeedItemWrapper> feedItemWrapperResponseEntity =
            starlingClient.GetFeedForAccount(
                account.getAccountUid(), account.getDefaultCategory(), account.getCreatedAt());

        if (feedItemWrapperResponseEntity.getStatusCode() == HttpStatus.OK) {
          return feedItemWrapperResponseEntity.getBody();
        }
      }
    }
    return new FeedItemWrapper();
  }
}
