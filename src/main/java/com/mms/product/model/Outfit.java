package com.mms.product.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public class Outfit {

  Map<Category, Product> outfit;

  public void add(Category category, Product product) {
    //TODO: 이미 존재하는 카테고리가 들어온다면 어떻게 해야하나?
    outfit.put(category, product);
  }

  public Map<Category, Product> getOutfit() {
    return outfit;
  }

  /**
   * 총 가격을 계산한다.
   *
   * @return 총 가격
   */
  public BigDecimal getTotalPrice() {
    return outfit.values().stream()
        .map(Product::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public static Outfit of() {
    return Outfit.builder()
        .outfit(new HashMap<>())
        .build();
  }
}
