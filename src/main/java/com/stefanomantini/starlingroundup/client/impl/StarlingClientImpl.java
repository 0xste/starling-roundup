package com.stefanomantini.starlingroundup.client.impl;

import com.stefanomantini.starlingroundup.client.contract.StarlingClient;
import com.stefanomantini.starlingroundup.client.dto.AccountWrapper;
import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class StarlingClientImpl implements StarlingClient {

  @Autowired protected final RestTemplate restTemplate;

  private final String authorizationHeader = "Authorization";
  private final SimpleDateFormat rfc3339DateFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

  private final String baseUrl;
  private final String bearerToken;
  private final String accountPath;
  private final String feedPath;

  public StarlingClientImpl(
      @Value("${starling.baseUrl}") final String baseUrl,
      @Value("${starling.bearerToken}") final String bearerToken,
      @Value("${starling.account.path}") final String accountPath,
      @Value("${starling.feed.path}") final String feedPath,
      final RestTemplate restTemplate) {
    this.baseUrl = baseUrl;
    this.bearerToken = bearerToken;
    this.accountPath = accountPath;
    this.feedPath = feedPath;
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEntity<AccountWrapper> GetAccountDetails() {
    final HttpHeaders headers = new HttpHeaders();
    headers.add(authorizationHeader, "Bearer " + bearerToken);
    return restTemplate.exchange(
            baseUrl + accountPath, HttpMethod.GET, new HttpEntity(headers), AccountWrapper.class);
  }

  @Override
  public ResponseEntity<FeedItemWrapper> GetFeedForAccount(
      final UUID accountId, final UUID categoryId, final LocalDateTime changesSince) {
    final HttpHeaders headers = new HttpHeaders();
    // todo split into separate function and validate template values
    String feedEndpoint = feedPath.replace("{accountId}", accountId.toString());
    feedEndpoint = feedEndpoint.replace("{categoryId}", categoryId.toString());
    feedEndpoint = feedEndpoint.replace("{changesSinceTimestamp}", changesSince.toString());
    feedEndpoint = baseUrl + feedEndpoint;
    headers.add(authorizationHeader, "Bearer " + bearerToken);
    return restTemplate.exchange(
        feedEndpoint, HttpMethod.GET, new HttpEntity(headers), FeedItemWrapper.class);
  }
}
