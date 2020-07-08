package com.stefanomantini.starlingroundup.api.contract;

import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

public interface RoundupController extends RoundupControllerContract {
  @RequestMapping(
      value = "account/{accountId}/round-up/{savingsGoalId}",
      method = RequestMethod.PUT)
  FeedItemWrapper roundupForPeriod(
      @PathVariable String accountId,
      @PathVariable String savingsGoalId,
      @RequestParam LocalDateTime fromDate,
      @RequestParam LocalDateTime toDate);
}
