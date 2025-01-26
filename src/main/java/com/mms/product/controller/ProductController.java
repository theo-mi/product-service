package com.mms.product.controller;

import com.mms.product.model.request.ProductRequest;
import com.mms.product.model.response.error.DefaultErrorResponse;
import com.mms.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 API", description = "상품과 관련된 API 목록입니다.")
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Validated
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "상품 추가", description = "등록된 상품의 id를 반환.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "등록 완료"),
      @ApiResponse(responseCode = "400", description = "삭제 실패", content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음.", content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))),
  })
  @PostMapping
  public ResponseEntity<Long> addProduct(@RequestBody @Valid ProductRequest request) {

    final Long productId = productService.add(request.getCategoryId(), request.getBrandId(), request.getPrice());

    return ResponseEntity.ok(productId);
  }

  @Operation(summary = "상품 수정", description = "수정된 상품의 id를 반환.")
  @Parameters(
      @Parameter(name = "id", description = "상품 id", example = "73", required = true)
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "수정 완료"),
      @ApiResponse(responseCode = "400", description = "삭제 실패", content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음.", content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))),
  })
  @PutMapping("/{id}")
  public ResponseEntity<Long> updateProduct(@PathVariable @Min(1) Long id, @RequestBody @Valid ProductRequest request) {
    final Long productId = productService.update(id, request.getCategoryId(), request.getBrandId(), request.getPrice());

    return ResponseEntity.ok(productId);
  }

  @Operation(summary = "상품 삭제", description = "삭제된 상품의 id를 반환.")
  @Parameters(
      @Parameter(name = "id", description = "상품 id", example = "73", required = true)
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "삭제 완료"),
      @ApiResponse(responseCode = "400", description = "삭제 실패", content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음.", content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteProduct(@PathVariable @Min(1) Long id) {
    final Long productId = productService.delete(id);

    return ResponseEntity.ok(productId);
  }
}
