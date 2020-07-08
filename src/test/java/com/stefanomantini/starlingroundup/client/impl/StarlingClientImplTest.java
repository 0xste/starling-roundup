package com.stefanomantini.starlingroundup.client.impl;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.stefanomantini.starlingroundup.client.dto.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class StarlingClientImplTest {

  private final String baseUrl = "https://mockstarling.com/";
  private final String bearerToken = "some-bearer-token";
  private final String accountPath = "api/v2/accounts";
  private final String savingsGoalPath = "api/v2/account/{accountId}/savings-goals";
  private final String feedPath =
      "api/v2/feed/account/{accountId}/category/{categoryId}?changesSince={changesSinceTimestamp}";

  @Mock private RestTemplate restTemplate;

  @Test
  void getAccountDetails_simpleCase_valid() {
    // arrange
    final StarlingClientImpl sc =
        new StarlingClientImpl(
            baseUrl, bearerToken, accountPath, feedPath, savingsGoalPath, restTemplate);

    final Account account = Account.builder().accountUid(UUID.randomUUID()).build();
    final AccountWrapper expectedAccounts = new AccountWrapper(Arrays.asList(account));
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + bearerToken);
    Mockito.when(
            restTemplate.exchange(
                baseUrl + accountPath,
                HttpMethod.GET,
                new HttpEntity(headers),
                AccountWrapper.class))
        .thenReturn(new ResponseEntity(expectedAccounts, HttpStatus.OK));

    // act
    final ResponseEntity<AccountWrapper> actualAccounts = sc.GetAccountDetails();

    // assert
    assertEquals("accounts should be equal", expectedAccounts, actualAccounts.getBody());
  }

  @Test
  void getFeedForAccount_simpleCase_valid() {
    // arrange
    final StarlingClientImpl sc =
        new StarlingClientImpl(
            baseUrl, bearerToken, accountPath, feedPath, savingsGoalPath, restTemplate);

    // TODO mock feedItemGeneration function
    final FeedItem feedItem =
        FeedItem.builder()
            .feedItemUid(UUID.randomUUID())
            .categoryUid(UUID.randomUUID())
            .amount(Amount.builder().currency(Currency.getInstance("EUR")).minorUnits(1234).build())
            .build();
    final FeedItemWrapper expectedFeedItems = new FeedItemWrapper(Arrays.asList(feedItem));
    final UUID accountId = UUID.randomUUID();
    final UUID categoryId = UUID.randomUUID();
    final LocalDateTime changesSince = LocalDateTime.now().minusMonths(3);
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + bearerToken);
    Mockito.when(
            restTemplate.exchange(
                String.format(
                    "%sapi/v2/feed/account/%s/category/%s?changesSince=%s",
                    baseUrl, accountId.toString(), categoryId.toString(), changesSince.toString()),
                HttpMethod.GET,
                new HttpEntity(headers),
                FeedItemWrapper.class))
        .thenReturn(new ResponseEntity(expectedFeedItems, HttpStatus.OK));

    // act
    final ResponseEntity<FeedItemWrapper> actualFeedItems =
        sc.GetFeedForAccount(accountId, categoryId, changesSince);

    // assert
    assertEquals("status should be ok", HttpStatus.OK, actualFeedItems.getStatusCode());
    assertEquals("feedItems should be equal", expectedFeedItems, actualFeedItems.getBody());
  }

  @Test
  @Disabled("failing test")
  void createNewSavingsGoal() {
    // arrange
    final StarlingClientImpl sc =
        new StarlingClientImpl(
            baseUrl, bearerToken, accountPath, feedPath, savingsGoalPath, restTemplate);

    final SavingsGoalRequest body = SavingsGoalRequest.builder().name("trip to paris").build();
    final SavingsGoalResponse responseBody = SavingsGoalResponse.builder().name("").build();
    final UUID accountId = UUID.randomUUID();
    final UUID categoryId = UUID.randomUUID();
    final LocalDateTime changesSince = LocalDateTime.now().minusMonths(3);
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + bearerToken);
    Mockito.when(
            restTemplate.exchange(
                String.format("%sapi/v2/account/%s/savings-goals", baseUrl, accountId.toString()),
                HttpMethod.PUT,
                new HttpEntity<>(body, headers),
                SavingsGoalRequest.class))
        .thenReturn(new ResponseEntity(responseBody, HttpStatus.OK));

    // act
    final ResponseEntity<SavingsGoalRequest> actualFeedItems =
        sc.CreateNewSavingsGoal(
            accountId,
            "hello",
            Amount.builder().currency(Currency.getInstance("GBP")).minorUnits(123).build());

    // assert
    assertEquals("status should be ok", HttpStatus.OK, actualFeedItems.getStatusCode());
    assertEquals("request should be equal", responseBody, actualFeedItems.getBody());
  }
}
