package com.mms.product.model.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.mms.product.utils.FixtureUtils;
import org.junit.jupiter.api.Test;

public class BrandTest {

  @Test
  void 브랜드명을_수정하면_변경된_이름이_반영된다() {
    // given
    Brand brand = FixtureUtils.monkey.giveMeOne(Brand.class);
    String newName = "새로운 브랜드명";

    // when
    brand.updateName(newName);

    // then
    assertThat(brand.getName()).isEqualTo(newName);
  }
}