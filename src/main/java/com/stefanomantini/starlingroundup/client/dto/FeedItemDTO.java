package com.stefanomantini.starlingroundup.client.dto;

import com.stefanomantini.starlingroundup.client.enumeration.Direction;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FeedItemDTO {
  private UUID feedItemUid;
  private UUID categoryUid;
  private AmountDTO amount;
  private AmountDTO sourceAmount;
  private Direction direction;
  private ZonedDateTime updatedAt;
  private ZonedDateTime transactionTime;
  private ZonedDateTime settlementTime;
  private String source;
  private String status;
  private String counterPartyType;
  private UUID counterPartyUid;
  private String counterPartyName;
  private UUID counterPartySubEntityUid;
  private String counterPartySubEntityName;
  private String counterPartySubEntityIdentifier;
  private String counterPartySubEntitySubIdentifier;
  private String reference;
  private String country;
  private String spendingCategory;
  private Boolean hasAttachment;
}
