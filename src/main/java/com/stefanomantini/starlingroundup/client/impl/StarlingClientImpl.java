package com.stefanomantini.starlingroundup.client.impl;

import com.stefanomantini.starlingroundup.client.contract.StarlingClient;
import com.stefanomantini.starlingroundup.client.dto.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StarlingClientImpl extends AbstractRestClient implements StarlingClient {

  @Override
  public ResponseEntity<Account[]> GetAccountDetails() {
    return restTemplate.getForEntity(baseUrl + accountPath, Account[].class);
  }

  public StarlingClientImpl(
      final String baseUrl,
      final String bearerToken,
      final String accountPath,
      final String feedPath,
      final RestTemplate restTemplate) {
    super(baseUrl, bearerToken, accountPath, feedPath, restTemplate);
  }
}
