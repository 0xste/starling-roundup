package com.stefanomantini.starlingroundup.api;

import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapper;
import com.stefanomantini.starlingroundup.service.contract.RoundupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoundupController {

  @Autowired RoundupService roundupService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public FeedItemWrapper getRoundupForAccount() {
    return roundupService.getRoundupAmount();
  }
}
