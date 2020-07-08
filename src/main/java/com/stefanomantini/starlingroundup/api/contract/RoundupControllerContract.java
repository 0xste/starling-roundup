package com.stefanomantini.starlingroundup.api.contract;

import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import io.swagger.annotations.*;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RoundupControllerContract {

  @ApiOperation(
      value = "Rounds up transactions between a given period",
      notes =
          "For a customer, take all the transactions in a given period and round them up to the nearest pound, then transfer to a savings goal")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Roundup Successful"),
    @ApiResponse(code = 201, message = "Roundup Successful, no roundup required"),
    @ApiResponse(code = 400, message = "Bad request, invalid parameters"),
    @ApiResponse(code = 500, message = "Error while processing the request")
  })
  @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "traceId", required = false)})
  public FeedItemWrapper roundupForPeriod(
      @PathVariable final String accountId,
      @PathVariable final String savingsGoalId,
      @RequestParam final LocalDateTime fromDate,
      @RequestParam final LocalDateTime toDate);
}
