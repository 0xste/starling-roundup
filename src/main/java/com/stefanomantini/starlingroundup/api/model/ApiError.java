package com.stefanomantini.starlingroundup.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiError {
  // todo align this format with the spring default error response handler
  String error;
}
