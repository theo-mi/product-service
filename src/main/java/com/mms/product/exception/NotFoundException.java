package com.mms.product.exception;

/**
 * 데이터를 찾을 수 없을 때 발생하는 예외
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

}
