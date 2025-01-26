package com.mms.product.exception;

import com.mms.product.model.response.DefaultErrorDetailResponse;
import com.mms.product.model.response.DefaultErrorResponse;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
    List<DefaultErrorDetailResponse> errorDetails = e.getParameterValidationResults().stream()
        .map(result ->
            DefaultErrorDetailResponse.builder()
                .field(result.getMethodParameter().getParameterName())
                .value(Objects.requireNonNull(result.getArgument()).toString())
                .message(result.getResolvableErrors().getFirst().getDefaultMessage())
                .build())
        .toList();

    return ResponseEntity
        .badRequest()
        .body(DefaultErrorResponse.fail("입력값에 오류가 있습니다.", errorDetails));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

    List<DefaultErrorDetailResponse> errorDetails = e.getFieldErrors().stream()
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
