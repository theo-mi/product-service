package com.mms.product.model.response.error;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class DefaultErrorResponse {

  private String message;

  @Singular
  private List<DefaultErrorDetailResponse> details;


  public static DefaultErrorResponse fail(String message) {
    return DefaultErrorResponse.builder()
        .message(message)
        .build();
  }

  public static DefaultErrorResponse fail(String message, List<DefaultErrorDetailResponse> details) {
    return DefaultErrorResponse.builder()
        .message(message)
        .details(details)
        .build();
  }
}
