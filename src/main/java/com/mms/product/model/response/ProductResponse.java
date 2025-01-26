package com.mms.product.model.response;

import com.mms.product.model.entity.Product;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductResponse {

  private Long id;
  private Long categoryId;
  private String categoryName;
  private Long brandId;
  private String brandName;
  private BigDecimal price;

  public static ProductResponse from(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .categoryId(product.getCategory().getId())
        .categoryName(product.getCategory().getName())
        .brandId(product.getBrand().getId())
        .brandName(product.getBrand().getName())
        .price(product.getPrice())
        .build();
  }
}
