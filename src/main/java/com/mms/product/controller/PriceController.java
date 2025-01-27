package com.mms.product.controller;

import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import com.mms.product.model.response.product.MinMaxProductResponse;
import com.mms.product.service.CategoryService;
import com.mms.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "가격 API", description = "가격과 관련된 API 목록입니다.")
@RestController
@RequestMapping("prices")
@RequiredArgsConstructor
@Validated
public class PriceController {

  private final CategoryService categoryService;
  private final ProductService productService;

  @Operation(summary = "카테고리별 최저가, 최고가 조회", description = "카테고리별 최저/최고가 반환.")
  @Parameters(
      @Parameter(name = "categoryName", description = "카테고리명", example = "상의", required = true)
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공"),
  })
  @GetMapping("/min-max")
  public ResponseEntity<MinMaxProductResponse> getMinMaxPriceByCategory(@NotBlank String categoryName) {
    final Category category = categoryService.getByName(categoryName);

    List<Product> cheapestProducts = productService.getCheapestByCategory(category);
    List<Product> expensiveProducts = productService.getExpensiveByCategory(category);

    MinMaxProductResponse minMaxProductResponse = MinMaxProductResponse.from(category, cheapestProducts, expensiveProducts);

    return ResponseEntity.ok(minMaxProductResponse);
  }
}
