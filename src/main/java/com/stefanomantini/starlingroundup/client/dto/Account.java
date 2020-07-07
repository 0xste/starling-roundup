package com.stefanomantini.starlingroundup.client.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account {
  public UUID accountUid;
  public UUID defaultCategory;
  public Currency currency;
  public LocalDateTime createdAt;
}
