package com.mms.product.model.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class DefaultErrorResponse {

  private String message;

  public static DefaultErrorResponse of(String message) {
    return DefaultErrorResponse.builder()
        .message(message)
        .build();
  }
}
