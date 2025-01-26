package com.mms.product.controller;

import com.mms.product.model.request.ProductRequest;
import com.mms.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  /**
   * 상품을 추가한다.
   * @param request 상품 추가 요청
   * @return 상품 ID
   */
  @PostMapping
  public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {

    final Long productId = productService.add(request.getCategoryId(), request.getBrandId(), request.getPrice());

    return ResponseEntity.ok(productId);
  }

  /**
   * 상품을 수정한다.
   * @param id 상품 ID
   * @param request 상품 수정 요청
   * @return 상품 ID
   */
  @PutMapping("/{id}")
  public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
    final Long productId = productService.update(id, request.getCategoryId(), request.getBrandId(), request.getPrice());

    return ResponseEntity.ok(productId);
  }

  /**
   * 상품을 삭제한다.
   * @param id 상품 ID
   * @return 상품 ID
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
    final Long productId = productService.delete(id);

    return ResponseEntity.ok(productId);
  }
}
