package com.mms.product.enums.exception;

public interface ErrorFormat {

  RuntimeException toException(Object... args);
}
