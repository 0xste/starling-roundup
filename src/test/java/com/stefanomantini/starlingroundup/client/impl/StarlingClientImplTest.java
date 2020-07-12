package com.stefanomantini.starlingroundup.client.impl;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.stefanomantini.starlingroundup.client.dto.*;
import com.stefanomantini.starlingroundup.client.exception.RemoteGatewayException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
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

  private final String userAgent = "some-agent";
  private final String baseUrl = "https://mockstarling.com/";
  private final String bearerToken = "some-bearer-token";
  private final String accountPath = "api/v2/accounts";
  private final String savingsGoalPath = "api/v2/account/{accountId}/savings-goals";
  private final String feedPath =
      "api/v2/feed/account/{accountId}/category/{categoryId}?changesSince={changesSinceTimestamp}";

  @Mock private RestTemplate restTemplate;

  private HttpHeaders defaultHeaders() {
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + bearerToken);
    headers.add("User-Agent", userAgent);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return headers;
  }

  @Test
  void getAccountDetails_simpleCase_valid() throws RemoteGatewayException {
    // arrange
    final StarlingClientImpl sc =
        new StarlingClientImpl(
            baseUrl, bearerToken, userAgent, accountPath, feedPath, savingsGoalPath, restTemplate);

    final AccountDTO account = AccountDTO.builder().accountUid(UUID.randomUUID()).build();
    final AccountWrapperDTO expectedAccounts = new AccountWrapperDTO(Arrays.asList(account));
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + bearerToken);
    Mockito.when(
            restTemplate.exchange(
                baseUrl + accountPath,
                HttpMethod.GET,
                new HttpEntity(defaultHeaders()),
                AccountWrapperDTO.class))
        .thenReturn(new ResponseEntity(expectedAccounts, HttpStatus.OK));

    // act
    final AccountWrapperDTO actualAccounts = sc.GetAccountDetails();

    // assert
    assertEquals("accounts should be equal", expectedAccounts, actualAccounts);
  }

  @Test
  void getFeedForAccount_simpleCase_valid() throws RemoteGatewayException {
    // arrange
    final StarlingClientImpl sc =
        new StarlingClientImpl(
            baseUrl, bearerToken, userAgent, accountPath, feedPath, savingsGoalPath, restTemplate);

    // TODO mock feedItemGeneration function
    final FeedItemDTO feedItem =
        FeedItemDTO.builder()
            .feedItemUid(UUID.randomUUID())
            .categoryUid(UUID.randomUUID())
            .amount(
                AmountDTO.builder().currency(Currency.getInstance("EUR")).minorUnits(1234).build())
            .build();
    final FeedItemWrapperDTO expectedFeedItems = new FeedItemWrapperDTO(Arrays.asList(feedItem));
    final UUID accountId = UUID.randomUUID();
    final UUID categoryId = UUID.randomUUID();
    final ZonedDateTime changesSince = ZonedDateTime.now().minusMonths(3);
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + bearerToken);
    Mockito.when(
            restTemplate.exchange(
                String.format(
                    "%sapi/v2/feed/account/%s/category/%s?changesSince=%s",
                    baseUrl,
                    accountId.toString(),
                    categoryId.toString(),
                    ZonedDateTime.ofInstant(changesSince.toInstant(), ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
                HttpMethod.GET,
                new HttpEntity(defaultHeaders()),
                FeedItemWrapperDTO.class))
        .thenReturn(new ResponseEntity(expectedFeedItems, HttpStatus.OK));

    // act
    final FeedItemWrapperDTO actualFeedItems =
        sc.GetFeedForAccount(accountId, categoryId, changesSince);

    // assert
    assertEquals("feedItems should be equal", expectedFeedItems, actualFeedItems);
  }

  @Test
  @Disabled("failing test")
  void createNewSavingsGoal() throws RemoteGatewayException {
    // arrange
    final StarlingClientImpl sc =
        new StarlingClientImpl(
            baseUrl, bearerToken, userAgent, accountPath, feedPath, savingsGoalPath, restTemplate);

    final CreateSavingsGoalRequestDTO body =
        CreateSavingsGoalRequestDTO.builder().name("trip to paris").build();
    final CreateSavingsGoalResponseDTO responseBody =
        CreateSavingsGoalResponseDTO.builder().build();
    final UUID accountId = UUID.randomUUID();
    final UUID categoryId = UUID.randomUUID();
    final LocalDateTime changesSince = LocalDateTime.now().minusMonths(3);
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + bearerToken);
    Mockito.when(
            restTemplate.exchange(
                String.format("%sapi/v2/account/%s/savings-goals", baseUrl, accountId.toString()),
                HttpMethod.PUT,
                new HttpEntity<>(body, defaultHeaders()),
                CreateSavingsGoalRequestDTO.class))
        .thenReturn(new ResponseEntity(responseBody, HttpStatus.OK));

    // act
    final CreateSavingsGoalResponseDTO actualFeedItems =
        sc.CreateNewSavingsGoal(
            accountId,
            "hello",
            AmountDTO.builder().currency(Currency.getInstance("GBP")).minorUnits(123).build());

    // assert
    assertEquals("request should be equal", responseBody, actualFeedItems);
  }
}
