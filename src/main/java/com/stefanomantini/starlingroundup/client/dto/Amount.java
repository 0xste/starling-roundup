package com.stefanomantini.starlingroundup.client.dto;

import lombok.Builder;

import java.util.Currency;

@Builder
public class Amount {
  private final Currency currency;
  private final Integer minorUnits;
}
