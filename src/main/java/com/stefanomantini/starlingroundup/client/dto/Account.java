package com.stefanomantini.starlingroundup.client.dto;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account {
  public UUID accountUuid;
  public UUID defaultCategory;
  public Currency currency;
  public LocalDateTime createdAt;
}
