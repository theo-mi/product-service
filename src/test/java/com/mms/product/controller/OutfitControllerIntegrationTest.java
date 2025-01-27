package com.mms.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class OutfitControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Nested
  class 최저가_코디_조회 {

    @Test
    void 모든_카테고리와_상품이_존재하면_200과_최저가_코디를_반환한다() throws Exception {
      // given
      // when & then
      mockMvc.perform(get("/outfits/cheapest"))
          .andExpect(status().isOk())
          // OutfitResponse 구조: { "products": [...], "totalPrice": ... }
          .andExpect(jsonPath("$.products").isArray())
          .andExpect(jsonPath("$.totalPrice").exists());
    }

    @Test
    @Sql(scripts = "/sql/clean_product.sql")
    void 상품이_아무것도_없다면_정상적으로_코디를_만들_수_없어_예외가_날_수도_있다() throws Exception {
      // given
      // when & then
      mockMvc.perform(get("/outfits/cheapest"))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  class 최저가_브랜드_코디_조회 {

    @Test
    void 모든_카테고리를_포함하는_브랜드가_있다면_200과_최저가브랜드_코디를_반환한다() throws Exception {
      // given
      // when & then
      mockMvc.perform(get("/outfits/cheapest/brand"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.cheapestOutfit").exists())
          .andExpect(jsonPath("$.cheapestOutfit.brandId").exists())
          .andExpect(jsonPath("$.cheapestOutfit.categories").isArray())
          .andExpect(jsonPath("$.cheapestOutfit.totalPrice").isNumber());
    }

    @Test
    @Sql(scripts = "/sql/clean_category_product.sql")
    void 어떤_브랜드도_모든_카테고리를_갖추지_못했다면_코디가_불가능하여_예외가_발생할_수_있다() throws Exception {
      // given
      // DB 내에 8개 카테고리 중 1개의 카테고리는 상품이 아예 없음.

      // when & then
      mockMvc.perform(get("/outfits/cheapest/brand"))
          .andExpect(status().isNotFound());
    }
  }
}
