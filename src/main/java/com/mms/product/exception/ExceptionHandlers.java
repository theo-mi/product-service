package com.mms.product.exception;

import com.mms.product.model.response.DefaultErrorDetailResponse;
import com.mms.product.model.response.DefaultErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<DefaultErrorResponse> handleNotFoundException(NotFoundException e) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(DefaultErrorResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<DefaultErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {

    return ResponseEntity
        .badRequest()
        .body(DefaultErrorResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<DefaultErrorResponse> handleValidateException(MethodArgumentNotValidException e) {

    List<DefaultErrorDetailResponse> errorDetails = e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> DefaultErrorDetailResponse.builder()
            .field(fieldError.getField())
            .value(String.valueOf(fieldError.getRejectedValue()))
            .message(fieldError.getDefaultMessage())
            .build())
        .toList();

    return ResponseEntity
        .badRequest()
        .body(DefaultErrorResponse.fail("입력값에 오류가 있습니다.", errorDetails));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<DefaultErrorResponse> handleException(Exception e) {
    return ResponseEntity
        .internalServerError()
        .body(DefaultErrorResponse.fail(e.getMessage()));
  }
}
