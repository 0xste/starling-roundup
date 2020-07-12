package com.stefanomantini.starlingroundup.client.dto;

import java.util.Currency;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSavingsGoalRequestDTO {
  String name;
  Currency currency;
  AmountDTO target;
  String base64EncodedPhoto;
}
