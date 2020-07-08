package com.stefanomantini.starlingroundup.client.dto;

import java.util.Currency;
import lombok.Builder;

@Builder
public class Amount {
  private final Currency currency;
  private final Integer minorUnits;
}
