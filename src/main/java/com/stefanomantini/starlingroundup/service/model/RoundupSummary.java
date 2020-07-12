package com.stefanomantini.starlingroundup.service.model;

import com.stefanomantini.starlingroundup.client.dto.AmountDTO;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoundupSummary {
  private UUID savingsGoalUid;
  private UUID accountId;
  private UUID transferUid;
  private AmountDTO totalAdded;
}
