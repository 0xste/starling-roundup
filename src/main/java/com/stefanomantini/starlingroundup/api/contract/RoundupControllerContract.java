package com.stefanomantini.starlingroundup.api.contract;

import com.stefanomantini.starlingroundup.service.exception.BusinessException;
import com.stefanomantini.starlingroundup.service.exception.TechnicalException;
import com.stefanomantini.starlingroundup.service.model.RoundupSummary;
import io.swagger.annotations.*;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RoundupControllerContract {

  @ApiOperation(
      value = "Rounds up transactions between a given period",
      notes =
          "For a customer, take all the transactions in a given period and round them up to the nearest pound, then transfer to a savings goal")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Roundup Successful"),
    @ApiResponse(code = 400, message = "Bad request, invalid parameters"),
    @ApiResponse(code = 500, message = "Error while processing the request")
  })
  @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "traceId")})
  public ResponseEntity<RoundupSummary> roundupForPeriod(
      @PathVariable final UUID accountId,
      @PathVariable final UUID savingsGoalId,
      @RequestParam final ZonedDateTime fromDate,
      @RequestParam final ZonedDateTime toDate)
      throws TechnicalException, BusinessException;
}
