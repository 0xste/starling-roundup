package com.stefanomantini.starlingroundup.client.impl;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.stefanomantini.starlingroundup.client.contract.StarlingClient;
import com.stefanomantini.starlingroundup.client.dto.Account;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class StarlingClientImplTest {

  @Autowired private StarlingClient starlingClient;

  @Mock private RestTemplate restTemplate;

  @Test
  void getAccountDetails() {
    // arrange
    final Account account = Account.builder().accountUuid(UUID.randomUUID()).build();
    final List<Account> expectedAccounts = Arrays.asList(account);
    Mockito.when(restTemplate.getForEntity(Mockito.any(), Mockito.any()))
        .thenReturn(new ResponseEntity(expectedAccounts, HttpStatus.OK));

    // act
    final ResponseEntity<Account[]> actualAccounts = starlingClient.GetAccountDetails();

    // assert
    assertEquals("accounts should be equal", expectedAccounts, actualAccounts);
  }
}
