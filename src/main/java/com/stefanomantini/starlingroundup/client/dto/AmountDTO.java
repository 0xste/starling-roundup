package com.stefanomantini.starlingroundup.client.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lombok.*;

// TODO make counterpart mapper for this as it's used extensively in service tier
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AmountDTO {

  Currency currency;

  // TODO this probably should be BigInteger, but for speed will be left at this for the demo
  Integer minorUnits;

  public BigDecimal toDecimalGbp() {
    return BigDecimal.valueOf(getMinorUnits()).movePointLeft(2).setScale(2);
  }

  public Integer getRoundup(final Integer multiplier) {
    // half even for the value from the api, abs to handle negatives
    final BigDecimal amount = toDecimalGbp().setScale(2, RoundingMode.HALF_EVEN).abs();
    // rounding up to scale the value to the next whole
    final BigDecimal scaled = amount.setScale(0, RoundingMode.UP);
    return scaled.subtract(amount).movePointRight(2).intValue() * multiplier;
  }
}
