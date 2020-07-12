package com.stefanomantini.starlingroundup.service.impl;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.stefanomantini.starlingroundup.client.dto.AmountDTO;
import com.stefanomantini.starlingroundup.service.contract.RoundupService;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RoundupServiceImplTest {

  public static final Currency gbp = Currency.getInstance("GBP");

  @ParameterizedTest
  @MethodSource("provideCalculateTotalFromSummaryArgs")
  void calculateTotalFromSummary(final IntStream amountValues, final Integer expected) {
    final RoundupService rs = new RoundupServiceImpl();
    final List<AmountDTO> amounts =
        amountValues
            .mapToObj(a -> AmountDTO.builder().currency(gbp).minorUnits(a).build())
            .collect(Collectors.toList());

    final Integer actual = rs.calculateTotalRoundup(amounts);

    assertEquals("roundup amount should be equal", expected, actual);
  }

  private static Stream<Arguments> provideCalculateTotalFromSummaryArgs() {
    return Stream.of(
        Arguments.of(IntStream.builder().add(0).build(), 0),
        Arguments.of(IntStream.builder().add(1).build(), 99),
        Arguments.of(IntStream.builder().add(12345).add(1344).add(123).build(), 188),
        Arguments.of(IntStream.builder().add(123123).add(12312312).add(12123123).build(), 242),
        Arguments.of(IntStream.builder().add(Integer.MAX_VALUE).add(1).add(1).add(1).build(), 350),
        Arguments.of(
            IntStream.builder().add(Integer.MAX_VALUE).add(Integer.MAX_VALUE).build(), 106),
        Arguments.of(
            IntStream.builder()
                .add(1)
                .add(1)
                .add(1)
                .add(1)
                .add(1)
                .add(1)
                .add(1)
                .add(1)
                .add(1)
                .add(1)
                .build(),
            990));
  }
}
