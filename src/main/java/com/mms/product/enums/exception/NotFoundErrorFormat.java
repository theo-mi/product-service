package com.mms.product.enums.exception;

import com.mms.product.exception.NotFoundException;

public enum NotFoundErrorFormat implements ErrorFormat {
  CATEGORY_ID("해당 카테고리가 존재하지 않습니다. (id: %d)"),
  CATEGORY_NAME("해당 카테고리가 존재하지 않습니다. (name: %s)"),
  BRAND_ID("해당 브랜드가 존재하지 않습니다. (id: %d)"),
  PRODUCT_ID("해당 상품이 존재하지 않습니다. (id: %d)");

  private final String message;

  NotFoundErrorFormat(String message) {
    this.message = message;
  }

  @Override
  public NotFoundException toException(Object... args) {
    return new NotFoundException(String.format(message, args));
  }
}
