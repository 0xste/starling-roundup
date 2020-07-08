package com.stefanomantini.starlingroundup.client.dto;

import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SavingsGoalRequest {
  private String name;
  private Currency currency;
  private Amount target;
  private String base64EncodedPhoto;
}
