package com.mms.product.controller;

import com.mms.product.model.request.BrandRequest;
import com.mms.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "브랜드 API", description = "브랜드와 관련된 API 목록입니다.")
@RestController
@RequestMapping("brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @Operation(summary = "브랜드 추가", description = "등록된 브랜드의 id를 반환.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "등록 완료"),
  })
  @PostMapping
  public ResponseEntity<Long> addBrand(@Valid @RequestBody BrandRequest request) {
    final Long brandId = brandService.add(request.getName());

    return ResponseEntity.ok(brandId);
  }

  @Operation(summary = "브랜드 수정", description = "수정된 브랜드의 id를 반환.")
  @Parameters(
      @Parameter(name = "id", description = "브랜드 id", example = "10", required = true)
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "수정 완료"),
  })
  @PutMapping("/{id}")
  public ResponseEntity<Long> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandRequest request) {
    final Long brandId = brandService.update(id, request.getName());

    return ResponseEntity.ok(brandId);
  }

  @Operation(summary = "브랜드 삭제", description = "삭제된 브랜드의 id를 반환.")
  @Parameters(
      @Parameter(name = "id", description = "브랜드 id", example = "10", required = true)
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "삭제 완료"),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteBrand(@PathVariable Long id) {
    final Long brandId = brandService.delete(id);

    return ResponseEntity.ok(brandId);
  }
}
