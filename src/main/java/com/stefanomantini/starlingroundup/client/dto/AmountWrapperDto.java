package com.stefanomantini.starlingroundup.client.dto;

import lombok.*;

// TODO make counterpart mapper for this as it's used extensively in service tier
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmountWrapperDto {
  AmountDTO amount;
}
