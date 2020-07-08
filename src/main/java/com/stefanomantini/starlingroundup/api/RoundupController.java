package com.stefanomantini.starlingroundup.api;

import com.stefanomantini.starlingroundup.api.contract.RoundupControllerContract;
import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import com.stefanomantini.starlingroundup.service.contract.RoundupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/")
public class RoundupController implements RoundupControllerContract {

  @Autowired RoundupService roundupService;

  @Override
  @RequestMapping(
      value = "account/{accountId}/round-up/{savingsGoalId}",
      method = RequestMethod.PUT)
  public FeedItemWrapper roundupForPeriod(
      @PathVariable final String accountId,
      @PathVariable final String savingsGoalId,
      @RequestParam final LocalDateTime fromDate,
      @RequestParam final LocalDateTime toDate) {
    return roundupService.getRoundupAmount();
  }
}
