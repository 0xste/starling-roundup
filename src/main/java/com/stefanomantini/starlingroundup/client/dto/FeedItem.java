package com.stefanomantini.starlingroundup.client.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedItem {
  private UUID feedItemUid;
  private UUID categoryUid;
  private Amount amount;
  private Amount sourceAmount;
  private DirectionType direction;
  private LocalDateTime updatedAt;
  private LocalDateTime transactionTime;
  private LocalDateTime settlementTime;
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
