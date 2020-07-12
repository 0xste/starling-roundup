package com.stefanomantini.starlingroundup.client.dto;

import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AddMoneyToSavingsGoalResponseDTO {
  UUID transferUid;
  Boolean success;
  List<String> errors;
}
