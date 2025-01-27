package com.mms.product.model.response.product;

import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import com.mms.product.model.response.brand.BrandPriceResponses;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MinMaxProductResponse {

  private Long categoryId;
  private String categoryName;
  private BrandPriceResponses minPrices;
  private BrandPriceResponses maxPrices;

  public static MinMaxProductResponse from(Category category, List<Product> minPriceProducts, List<Product> maxPriceProducts) {
    return MinMaxProductResponse.builder()
        .categoryId(category.getId())
        .categoryName(category.getName())
        .minPrices(BrandPriceResponses.from(minPriceProducts))
        .maxPrices(BrandPriceResponses.from(maxPriceProducts))
        .build();
  }
}
