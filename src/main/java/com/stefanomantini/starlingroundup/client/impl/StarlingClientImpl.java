package com.stefanomantini.starlingroundup.client.impl;

import com.stefanomantini.starlingroundup.client.contract.StarlingClient;
import com.stefanomantini.starlingroundup.client.dto.*;
import com.stefanomantini.starlingroundup.client.exception.RemoteGatewayException;
import com.stefanomantini.starlingroundup.service.exception.BusinessException;
import com.stefanomantini.starlingroundup.service.exception.TechnicalException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Currency;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class StarlingClientImpl implements StarlingClient {

  @Autowired protected final RestTemplate restTemplate;

  // TODO move boilerplate to AbstractApiClient and make protected
  private final String authorizationHeader = "Authorization";
  private final String userAgentHeader = "User-Agent";
  private final SimpleDateFormat rfc3339DateFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
  private final String InvalidResponseMessage = "invalid response recieved from client";

  private final String baseUrl;
  private final String bearerToken;
  private final String accountPath;
  private final String feedPath;
  private final String savingsGoalPath;
  private final String userAgent;

  public StarlingClientImpl(
      @Value("${starling.baseUrl}") final String baseUrl,
      @Value("${starling.bearerToken}") final String bearerToken,
      @Value("${starling.userAgent}") final String userAgent,
      @Value("${starling.account.path}") final String accountPath,
      @Value("${starling.feed.path}") final String feedPath,
      @Value("${starling.savingsGoal.path}") final String savingsGoalPath,
      final RestTemplate restTemplate) {
    this.baseUrl = baseUrl;
    this.bearerToken = bearerToken;
    this.accountPath = accountPath;
    this.feedPath = feedPath;
    this.savingsGoalPath = savingsGoalPath;
    this.restTemplate = restTemplate;
    this.userAgent = userAgent;
  }

  private HttpHeaders defaultHeaders() {
    final HttpHeaders headers = new HttpHeaders();
    headers.add(authorizationHeader, "Bearer " + bearerToken);
    headers.add(userAgentHeader, userAgent);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return headers;
  }

  @Override
  public AccountWrapperDTO GetAccountDetails() throws RemoteGatewayException {
    final ResponseEntity<AccountWrapperDTO> response =
        restTemplate.exchange(
            baseUrl + accountPath,
            HttpMethod.GET,
            new HttpEntity(defaultHeaders()),
            AccountWrapperDTO.class);

    if (response.getStatusCode() != HttpStatus.OK) {
      throw new RemoteGatewayException(InvalidResponseMessage);
    }
    return response.getBody();
  }

  @Override
  public FeedItemWrapperDTO GetFeedForAccount(
      final UUID accountId, final UUID categoryId, final ZonedDateTime changesSince)
      throws RemoteGatewayException {

    final UriComponents feedUri =
        UriComponentsBuilder.newInstance()
            .fromHttpUrl(baseUrl)
            .path(feedPath)
            .buildAndExpand(
                accountId.toString(),
                categoryId.toString(),
                ZonedDateTime.ofInstant(changesSince.toInstant(), ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

    final ResponseEntity<FeedItemWrapperDTO> response;
    try {
      response =
          restTemplate.exchange(
              feedUri.toString(),
              HttpMethod.GET,
              new HttpEntity(defaultHeaders()),
              FeedItemWrapperDTO.class);
    } catch (final HttpClientErrorException e) {
      throw new RemoteGatewayException(e.getLocalizedMessage());
    }
    if (response.getStatusCode() != HttpStatus.OK) {
      throw new RemoteGatewayException(
          InvalidResponseMessage + response.getStatusCode().getReasonPhrase());
    }
    return response.getBody();
  }

  @Override
  public CreateSavingsGoalResponseDTO CreateNewSavingsGoal(
      final UUID accountId, final String name, final AmountDTO target)
      throws RemoteGatewayException {
    String feedEndpoint = savingsGoalPath.replace("{accountId}", accountId.toString());
    feedEndpoint = baseUrl + feedEndpoint;

    final CreateSavingsGoalRequestDTO body =
        CreateSavingsGoalRequestDTO.builder()
            .currency(Currency.getInstance("GBP"))
            .name(name)
            .target(target)
            .build();
    final ResponseEntity<CreateSavingsGoalResponseDTO> response =
        restTemplate.exchange(
            feedEndpoint,
            HttpMethod.PUT,
            new HttpEntity(body, defaultHeaders()),
            CreateSavingsGoalResponseDTO.class);

    if (response.getStatusCode() != HttpStatus.OK) {
      throw new RemoteGatewayException(InvalidResponseMessage);
    }

    return response.getBody();
  }

  @Override
  public AddMoneyToSavingsGoalResponseDTO AddMoneyToSavingsGoal(
      final UUID accountId, final UUID savingsGoalId, final AmountDTO toAdd)
      throws RemoteGatewayException, BusinessException, TechnicalException {

    final UriComponents addMoneyUri =
        UriComponentsBuilder.newInstance()
            .fromHttpUrl(baseUrl)
            .path(savingsGoalPath + "/{savingsGoalId}/add-money/{tranferUid}")
            .buildAndExpand(accountId.toString(), savingsGoalId.toString(), UUID.randomUUID());
    final ResponseEntity<AddMoneyToSavingsGoalResponseDTO> response;
    try {
      response =
          restTemplate.exchange(
              addMoneyUri.toString(),
              HttpMethod.PUT,
              new HttpEntity(AmountWrapperDto.builder().amount(toAdd).build(), defaultHeaders()),
              AddMoneyToSavingsGoalResponseDTO.class);
      if (response.getStatusCode() != HttpStatus.OK) {
        throw new RemoteGatewayException(InvalidResponseMessage);
      }
      if (!response.getBody().getSuccess()) {
        log.error(response.getBody().getErrors().toString());
        throw new TechnicalException(response.getBody().getErrors().toString());
      }
    } catch (final HttpClientErrorException e) {
      throw new TechnicalException(e.getLocalizedMessage());
    }
    return response.getBody();
  }
}
