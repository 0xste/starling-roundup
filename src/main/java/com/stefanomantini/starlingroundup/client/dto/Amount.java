package com.stefanomantini.starlingroundup.client.dto;

import java.util.Currency;
import lombok.Builder;

// TODO make counterpart mapper for this as it's used extensively in service tier
@Builder
public class Amount {
  private final Currency currency;
  private final Integer minorUnits;
}
