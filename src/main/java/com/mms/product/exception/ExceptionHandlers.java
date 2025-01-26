package com.mms.product.exception;

import com.mms.product.model.response.error.DefaultErrorDetailResponse;
import com.mms.product.model.response.error.DefaultErrorResponse;
import jakarta.validation.ConstraintViolationException;
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

  /**
   * RequestBody의 Validation에 실패한 경우 발생하는 Exception을 처리한다.
   *
   * @param e HandlerMethodValidationException
   * @return ResponseEntity
   */
  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<DefaultErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
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

  /**
   * RequestParam, PathVariable의 Validation에 실패한 경우 발생하는 Exception을 처리한다.
   *
   * @param e
   * @return
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<DefaultErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
    List<DefaultErrorDetailResponse> errorDetails = e.getConstraintViolations().stream()
        .map(violation -> DefaultErrorDetailResponse.builder()
            .field(violation.getPropertyPath().toString())
            .value(String.valueOf(violation.getInvalidValue()))
            .message(violation.getMessage())
            .build())
        .toList();

    return ResponseEntity
        .badRequest()
        .body(DefaultErrorResponse.fail("입력값에 오류가 있습니다.", errorDetails));
  }

  /**
   * 스프링 3.2 이상에서 발생하는 Validation Exception을 처리한다. (현재는 막아둠)
   *
   * @param e MethodArgumentNotValidException
   * @return ResponseEntity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<DefaultErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

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
