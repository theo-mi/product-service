package com.mms.product.controller;

import com.mms.product.model.request.ProductRequest;
import com.mms.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "상품 추가", description = "등록된 상품의 id를 반환.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "등록 완료"),
  })
  @PostMapping
  public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {

    final Long productId = productService.add(request.getCategoryId(), request.getBrandId(), request.getPrice());

    return ResponseEntity.ok(productId);
  }

  @Operation(summary = "상품 수정", description = "수정된 상품의 id를 반환.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "수정 완료"),
  })
  @PutMapping("/{id}")
  public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
    final Long productId = productService.update(id, request.getCategoryId(), request.getBrandId(), request.getPrice());

    return ResponseEntity.ok(productId);
  }

  @Operation(summary = "상품 삭제", description = "삭제된 상품의 id를 반환.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "삭제 완료"),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
    final Long productId = productService.delete(id);

    return ResponseEntity.ok(productId);
  }
}
