package com.mms.product.model.response;

import com.mms.product.model.Outfit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CheapestBrandOutfitResponse {

  private CheapestBrandResponse cheapestOutfit;

  public static CheapestBrandOutfitResponse from(Outfit outfit) {
    return CheapestBrandOutfitResponse.builder()
        .cheapestOutfit(CheapestBrandResponse.from(outfit))
        .build();
  }
}
