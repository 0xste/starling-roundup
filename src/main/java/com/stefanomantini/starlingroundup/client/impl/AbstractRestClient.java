package com.stefanomantini.starlingroundup.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestClient {

  protected final String baseUrl;
  protected final String bearerToken;
  protected final String accountPath;
  protected final String feedPath;

  @Autowired protected final RestTemplate restTemplate;

  public AbstractRestClient(
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
}
