package com.mms.product.model.response.brand;

import com.mms.product.model.entity.Product;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class BrandPriceResponse {

  private Long brandId;
  private String brandName;
  private BigDecimal price;

  public static BrandPriceResponse from(Product product) {
    return BrandPriceResponse.builder()
        .brandId(product.getBrand().getId())
        .brandName(product.getBrand().getName())
        .price(product.getPrice())
        .build();
  }
}
