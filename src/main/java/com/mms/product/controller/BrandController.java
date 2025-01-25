package com.mms.product.controller;

import com.mms.product.model.request.BrandRequest;
import com.mms.product.service.BrandService;
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
@RequestMapping("brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  /**
   * 브랜드를 추가한다.
   *
   * @param request 브랜드 등록 요청
   * @return 브랜드 ID
   */
  @PostMapping
  public ResponseEntity<Long> addBrand(@RequestBody BrandRequest request) {
    final Long brandId = brandService.add(request.getName());

    return ResponseEntity.ok(brandId);
  }

  /**
   * 브랜드를 수정한다.
   *
   * @param id      브랜드 ID
   * @param request 브랜드 수정 요청
   * @return 브랜드 ID
   */
  @PutMapping("/{id}")
  public ResponseEntity<Long> updateBrand(@PathVariable Long id, @RequestBody BrandRequest request) {
    final Long brandId = brandService.update(id, request.getName());

    return ResponseEntity.ok(brandId);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteBrand(@PathVariable Long id) {
    final Long brandId = brandService.delete(id);

    return ResponseEntity.ok(brandId);
  }
}
