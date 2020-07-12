package com.stefanomantini.starlingroundup.client.dto;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AmountDTOTest {

  private static final Currency gbp = Currency.getInstance("GBP");

  @ParameterizedTest
  @MethodSource("provideToDecimalArgs")
  void toDecimalGbp(final Integer initialAmount, final BigDecimal expected) {
    final AmountDTO amount = AmountDTO.builder().currency(gbp).minorUnits(initialAmount).build();

    final BigDecimal actual = amount.toDecimalGbp();

    assertEquals("decimal should match", expected, actual);
  }

  private static Stream<Arguments> provideToDecimalArgs() {
    return Stream.of(
        Arguments.of(12345, new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP)),
        Arguments.of(0, new BigDecimal(00.00).setScale(2, RoundingMode.HALF_UP)),
        Arguments.of(-1000, new BigDecimal(-10.00).setScale(2, RoundingMode.HALF_UP)),
        Arguments.of(
            Integer.MAX_VALUE, new BigDecimal(21474836.47).setScale(2, RoundingMode.HALF_UP)),
        Arguments.of(
            Integer.MIN_VALUE, new BigDecimal(-21474836.48).setScale(2, RoundingMode.HALF_UP)));
  }

  @ParameterizedTest
  @MethodSource("provideGetRoundupAmountArgs")
  void getRoundupAmount(
      final Integer initialAmount, final Integer expectedRoundup, final Integer multiplier) {
    final AmountDTO amount = AmountDTO.builder().currency(gbp).minorUnits(initialAmount).build();

    final Integer actual = amount.getRoundup(multiplier);

    assertEquals("roundup value should match", expectedRoundup, actual);
  }

  private static Stream<Arguments> provideGetRoundupAmountArgs() {
    return Stream.of(
        Arguments.of(12345, 55, 1),
        Arguments.of(12345, 550, 10),
        Arguments.of(Integer.MAX_VALUE, 53, 1),
        Arguments.of(1001, 99, 1),
        Arguments.of(0, 0, 1),
        Arguments.of(-1090, 10, 1),
        Arguments.of(-1090, 30, 3),
        Arguments.of(Integer.MIN_VALUE, 52, 1));
  }
}
