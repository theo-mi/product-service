package com.mms.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Nested
  class 카테고리별_최저가_최고가_조회_API {

    @Test
    void 존재하는_카테고리명을_주면_200상태와_최저최고가_상품정보를_반환한다() throws Exception {
      // given
      String categoryName = "상의";

      // when & then
      mockMvc.perform(get("/prices/min-max")
              .param("categoryName", categoryName))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.categoryName").value(categoryName))
          .andExpect(jsonPath("$.minPrices").exists())
          .andExpect(jsonPath("$.maxPrices").exists());
    }

    @Test
    void 존재하지_않는_카테고리명을_주면_404가_발생한다() throws Exception {
      // given
      String invalidCategoryName = "존재하지않는카테고리";

      // when & then
      mockMvc.perform(get("/prices/min-max")
              .param("categoryName", invalidCategoryName))
          .andExpect(status().isNotFound());
    }

    @Test
    void 카테고리명을_빈값으로_주면_400상태가_발생한다() throws Exception {
      // given
      String emptyCategoryName = "";

      // when & then
      mockMvc.perform(get("/prices/min-max")
              .param("categoryName", emptyCategoryName))
          .andExpect(status().isBadRequest());
    }

    @Test
    void 카테고리명을_미입력하면_400상태가_발생한다() throws Exception {
      // given
      // when & then
      mockMvc.perform(get("/prices/min-max"))
          .andExpect(status().isBadRequest());
    }
  }
}
