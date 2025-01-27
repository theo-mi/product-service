package com.mms.product.model.response.outfit;

import com.mms.product.model.Outfit;
import com.mms.product.model.response.product.ProductResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OutfitResponse {

  private List<ProductResponse> products;
  private BigDecimal totalPrice;

  public static OutfitResponse from(Outfit outfit) {
    List<ProductResponse> productsResponse = outfit.getOutfit().values().stream()
        .map(ProductResponse::from)
        .toList();

    return OutfitResponse.builder()
        .products(productsResponse)
        .totalPrice(outfit.getTotalPrice())
        .build();
  }
}
