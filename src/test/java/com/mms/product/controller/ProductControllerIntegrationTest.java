package com.mms.product.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.product.model.request.ProductRequest;
import com.mms.product.utils.FixtureUtils;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Nested
  class 상품_등록_API {

    @Test
    void 유효한_상품정보로_등록하면_201_상태와_생성된_ID를_반환한다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 1L)
          .set("brandId", 1L)
          .set("price", BigDecimal.valueOf(12345))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(post("/products")
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$").value(notNullValue())); // ID 값이 반환됨
    }

    @Test
    void 카테고리ID가_유효하지않으면_404가_발생한다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 999L)
          .set("brandId", 1L)
          .set("price", BigDecimal.valueOf(1000))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(post("/products")
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isNotFound());
    }

    @Test
    void 브랜드ID가_유효하지않으면_404가_발생한다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 1L)
          .set("brandId", 999L)
          .set("price", BigDecimal.valueOf(1000))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(post("/products")
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isNotFound());
    }

    @Test
    void 상품가격이_0이하이면_400이_발생한다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 1L)
          .set("brandId", 1L)
          .sample();
      ReflectionTestUtils.setField(request, "price", BigDecimal.ZERO);
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(post("/products")
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class 상품_수정_API {

    @Test
    void 존재하지_않는_ID로_수정하면_404가_반환된다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 1L)
          .set("brandId", 1L)
          .set("price", BigDecimal.valueOf(5000))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/products/{id}", 9999)
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isNotFound());
    }

    @Test
    void ID가_1미만이면_400이_반환된다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 1L)
          .set("brandId", 1L)
          .set("price", BigDecimal.valueOf(5000))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/products/{id}", 0)
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isBadRequest()); // @Min(1) 실패
    }

    @Test
    void 유효한_ID와_상품정보면_200상태와_수정된_ID가_반환된다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 1L)
          .set("brandId", 1L)
          .set("price", BigDecimal.valueOf(99999))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/products/{id}", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").value(1));
    }

    @Test
    void 수정시_브랜드가_존재하지않으면_404가_발생한다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 1L)
          .set("brandId", 999L)
          .set("price", BigDecimal.valueOf(3000))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/products/{id}", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isNotFound());
    }

    @Test
    void 수정시_카테고리가_존재하지않으면_404가_발생한다() throws Exception {
      // given
      ProductRequest request = FixtureUtils.monkey.giveMeBuilder(ProductRequest.class)
          .set("categoryId", 999L)
          .set("brandId", 1L)
          .set("price", BigDecimal.valueOf(3000))
          .sample();
      String json = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/products/{id}", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .content(json))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  class 상품_삭제_API {

    @Test
    void 존재하지_않는_ID로_삭제하면_404상태가_반환된다() throws Exception {
      // given
      // when & then
      mockMvc.perform(delete("/products/{id}", 9999))
          .andExpect(status().isNotFound());
    }

    @Test
    void ID가_1미만이면_400상태가_반환된다() throws Exception {
      // given
      // when & then
      mockMvc.perform(delete("/products/{id}", 0))
          .andExpect(status().isBadRequest());
    }

    @Test
    void 정상적으로_삭제되면_200상태와_삭제된_ID가_반환된다() throws Exception {
      // given
      // when & then
      mockMvc.perform(delete("/products/{id}", 1))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").value(1));
    }
  }
}
