package com.mms.product.model.response.brand;

import com.mms.product.model.entity.Product;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class BrandPriceResponses {

  List<BrandPriceResponse> brandPriceResponses;

  public static BrandPriceResponses from(List<Product> brandPriceResponses) {
    List<BrandPriceResponse> responses = brandPriceResponses.stream()
        .map(BrandPriceResponse::from)
        .toList();

    return BrandPriceResponses.builder()
        .brandPriceResponses(responses)
        .build();
  }
}
