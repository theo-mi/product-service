package com.mms.product.model.response;

import com.mms.product.model.Outfit;
import com.mms.product.model.Product;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CheapestBrandResponse {

  private Long brandId;
  private String brandName;
  private List<CategoryPriceResponse> categories;
  private BigDecimal totalPrice;

  public static CheapestBrandResponse from(Outfit outfit) {
    Product product = outfit.getOutfit().values().stream()
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("코디가 완성되지 않았습니다."));

    List<CategoryPriceResponse> priceByCategories = outfit.getOutfit().values().stream()
        .map(CategoryPriceResponse::from)
        .toList();
    
    return CheapestBrandResponse.builder()
        .brandId(product.getBrand().getId())
        .brandName(product.getBrand().getName())
        .categories(priceByCategories)
        .totalPrice(outfit.getTotalPrice())
        .build();
  }
}
