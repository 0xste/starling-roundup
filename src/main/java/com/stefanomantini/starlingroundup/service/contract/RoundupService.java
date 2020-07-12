package com.stefanomantini.starlingroundup.service.contract;

import com.stefanomantini.starlingroundup.client.dto.AmountDTO;
import com.stefanomantini.starlingroundup.service.exception.BusinessException;
import com.stefanomantini.starlingroundup.service.exception.TechnicalException;
import com.stefanomantini.starlingroundup.service.model.RoundupSummary;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface RoundupService {
  RoundupSummary roundUpForPeriod(
      UUID accountId, UUID savingsGoalId, ZonedDateTime from, ZonedDateTime to)
      throws BusinessException, TechnicalException;

  Integer calculateTotalRoundup(List<AmountDTO> summary);
}
