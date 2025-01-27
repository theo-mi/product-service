package com.mms.product.model.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.mms.product.utils.FixtureUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ProductTest {

  @Test
  void 상품정보를_수정하면_변경된_정보가_반영된다() {
    // given
    Product product = FixtureUtils.monkey.giveMeOne(Product.class);
    Category newCategory = FixtureUtils.monkey.giveMeOne(Category.class);
    Brand newBrand = FixtureUtils.monkey.giveMeOne(Brand.class);
    BigDecimal newPrice = new BigDecimal("999.99");

    // when
    product.update(newCategory, newBrand, newPrice);

    // then
    assertThat(product.getCategory()).isEqualTo(newCategory);
    assertThat(product.getBrand()).isEqualTo(newBrand);
    assertThat(product.getPrice()).isEqualTo(newPrice);
  }

  @Test
  void 상품을_삭제하면_deletedAt이_설정된다() {
    // given
    Product product = FixtureUtils.monkey.giveMeOne(Product.class);

    // when
    product.delete();

    // then
    assertThat(product.getDeletedAt()).isNotNull();
  }
}