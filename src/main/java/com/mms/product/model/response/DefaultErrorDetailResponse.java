package com.mms.product.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultErrorDetailResponse {

  private String field;
  private String value;
  private String message;
}
