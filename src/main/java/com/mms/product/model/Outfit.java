package com.mms.product.model;

import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public class Outfit {

  Map<Category, Product> outfit;

  public void add(Category category, Product product) {
    if (outfit.containsKey(category)) {
      // 정확한 정책이 만들어지면 예외 처리를 변경해야 함. 현재는 동일 카테고리가 들어올 수 없는 로직이기에 막아둠.
      throw new IllegalArgumentException("이미 해당 코디의 카테고리에 상품이 존재합니다.");
    }

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

  /**
   * 현재 옷 조합의 가격이 outfit의 가격보다 더 저렴한지 확인한다.
   *
   * @param target 비교할 옷 조합
   * @return 현재 옷 조합이 더 저렴하면 true, 아니면 false
   */
  public boolean moreThanCheapest(Outfit target) {
    return this.getTotalPrice().compareTo(target.getTotalPrice()) < 0;
  }

  /**
   * 옷 조합이 비어있는지 확인한다.
   *
   * @return 옷 조합이 비어있으면 true, 아니면 false
   */
  public boolean isEmpty() {
    return outfit.isEmpty();
  }

  public static Outfit of() {
    return Outfit.builder()
        .outfit(new HashMap<>())
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Outfit other)) {
      return false;
    }
    return Objects.equals(outfit, other);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(outfit);
  }
}
