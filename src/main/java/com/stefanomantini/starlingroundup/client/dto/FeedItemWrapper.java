package com.stefanomantini.starlingroundup.client.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedItemWrapper {
  private List<FeedItem> feedItems;
}
