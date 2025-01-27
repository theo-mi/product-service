package com.mms.product.model.response.product;

import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import com.mms.product.model.response.brand.BrandPriceResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MinMaxProductResponse {

  private Long categoryId;
  private String categoryName;
  private List<BrandPriceResponse> minPrices;
  private List<BrandPriceResponse> maxPrices;

  public static MinMaxProductResponse from(Category category, List<Product> cheapestProducts, List<Product> expensiveProducts) {
    List<BrandPriceResponse> minPriceResponses = cheapestProducts.stream()
        .map(BrandPriceResponse::from)
        .toList();

    List<BrandPriceResponse> maxPriceResponses = expensiveProducts.stream()
        .map(BrandPriceResponse::from)
        .toList();

    return MinMaxProductResponse.builder()
        .categoryId(category.getId())
        .categoryName(category.getName())
        .minPrices(minPriceResponses)
        .maxPrices(maxPriceResponses)
        .build();
  }
}
