package com.mms.product.controller;

import com.mms.product.model.Category;
import com.mms.product.model.Product;
import com.mms.product.model.response.MinMaxProductResponse;
import com.mms.product.service.CategoryService;
import com.mms.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prices")
@RequiredArgsConstructor
public class PriceController {

  private final CategoryService categoryService;
  private final ProductService productService;

  /**
   * 카테고리별 최저가, 최고가 상품을 조회한다.
   *
   * @param categoryName 카테고리 이름
   * @return 카테고리별 최저가, 최고가 상품
   */
  @GetMapping("/min-max")
  public ResponseEntity<MinMaxProductResponse> getMinMaxPriceByCategory(String categoryName) {
    final Category category = categoryService.getByName(categoryName);

    final Product cheapestProduct = productService.getCheapestByCategory(category);
    final Product expensiveProduct = productService.getExpensiveByCategory(category);

    MinMaxProductResponse minMaxProductResponse = MinMaxProductResponse.from(category, cheapestProduct, expensiveProduct);

    return ResponseEntity.ok(minMaxProductResponse);
  }
}
