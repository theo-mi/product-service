package com.mms.product.enums.exception;

public interface ErrorFormat {

  String getMessage();

  RuntimeException toException(Object... args);
}
