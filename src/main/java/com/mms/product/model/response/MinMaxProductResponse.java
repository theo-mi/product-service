package com.mms.product.model.response;

import com.mms.product.model.Category;
import com.mms.product.model.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MinMaxProductResponse {

  private Long categoryId;
  private String categoryName;
  private BrandPriceResponse minPrice;
  private BrandPriceResponse maxPrice;

  public static MinMaxProductResponse from(Category category, Product minPriceProduct, Product maxPriceProduct) {
    return MinMaxProductResponse.builder()
        .categoryId(category.getId())
        .categoryName(category.getName())
        .minPrice(BrandPriceResponse.from(minPriceProduct))
        .maxPrice(BrandPriceResponse.from(maxPriceProduct))
        .build();
  }
}
