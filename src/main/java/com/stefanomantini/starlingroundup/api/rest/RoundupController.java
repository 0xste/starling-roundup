package com.stefanomantini.starlingroundup.api.rest;

import com.stefanomantini.starlingroundup.api.contract.RoundupControllerContract;
import com.stefanomantini.starlingroundup.service.contract.RoundupService;
import com.stefanomantini.starlingroundup.service.exception.BusinessException;
import com.stefanomantini.starlingroundup.service.exception.TechnicalException;
import com.stefanomantini.starlingroundup.service.model.RoundupSummary;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RoundupController implements RoundupControllerContract {

  @Autowired RoundupService roundupService;

  @Override
  @RequestMapping(
      value = "/api/v1/account/{accountId}/round-up/{savingsGoalId}",
      method = RequestMethod.PUT)
  public ResponseEntity<RoundupSummary> roundupForPeriod(
      @PathVariable final UUID accountId,
      @PathVariable final UUID savingsGoalId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          final ZonedDateTime fromDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime toDate)
      throws TechnicalException, BusinessException {
    // todo validate input params
    return ResponseEntity.ok(
        roundupService.roundUpForPeriod(accountId, savingsGoalId, fromDate, toDate));
  }
}
