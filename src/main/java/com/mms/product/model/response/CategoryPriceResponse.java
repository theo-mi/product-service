package com.mms.product.model.response;

import com.mms.product.model.entity.Product;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CategoryPriceResponse {

  private Long categoryId;
  private String categoryName;
  private BigDecimal price;

  public static CategoryPriceResponse from(Product product) {
    return CategoryPriceResponse.builder()
        .categoryId(product.getCategory().getId())
        .categoryName(product.getCategory().getName())
        .price(product.getPrice())
        .build();
  }
}
