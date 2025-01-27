package com.mms.product.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import com.mms.product.utils.FixtureUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class OutfitTest {

  @Test
  void 특정_카테고리의_상품을_추가하면_코디에_반영된다() {
    // given
    Outfit outfit = Outfit.of();
    Category category = FixtureUtils.monkey.giveMeOne(Category.class);
    Product product = FixtureUtils.monkey.giveMeOne(Product.class);

    // when
    outfit.add(category, product);

    // then
    assertThat(outfit.getOutfit().get(category)).isEqualTo(product);
  }

  @Test
  void 총_가격을_계산하면_정확한_가격이_반환된다() {
    // given
    Outfit outfit = Outfit.of();
    Product product1 = FixtureUtils.monkey.giveMeOne(Product.class);
    Product product2 = FixtureUtils.monkey.giveMeOne(Product.class);
    outfit.add(FixtureUtils.monkey.giveMeOne(Category.class), product1);
    outfit.add(FixtureUtils.monkey.giveMeOne(Category.class), product2);
    BigDecimal expectedTotalPrice = product1.getPrice().add(product2.getPrice());

    // when
    BigDecimal totalPrice = outfit.getTotalPrice();

    // then
    assertThat(totalPrice).isEqualTo(expectedTotalPrice);
  }

  @Test
  void 옷조합이_비어있는지_확인하면_정확한_결과가_반환된다() {
    // given
    Outfit emptyOutfit = Outfit.of();
    Outfit nonEmptyOutfit = Outfit.of();
    nonEmptyOutfit.add(FixtureUtils.monkey.giveMeOne(Category.class), FixtureUtils.monkey.giveMeOne(Product.class));

    // when
    boolean isEmpty = emptyOutfit.isEmpty();
    boolean isNotEmpty = nonEmptyOutfit.isEmpty();

    // then
    assertThat(isEmpty).isTrue();
    assertThat(isNotEmpty).isFalse();
  }
}