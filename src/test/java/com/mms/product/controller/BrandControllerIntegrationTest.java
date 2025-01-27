package com.mms.product.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.product.model.request.BrandRequest;
import com.mms.product.utils.FixtureUtils;
import jakarta.transaction.Transactional;
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
class BrandControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Nested
  class 브랜드_등록_API {

    @Test
    void 브랜드를_정상적으로_등록하면_201상태와_생성된_id를_반환한다() throws Exception {
      // given
      BrandRequest request = FixtureUtils.monkey.giveMeBuilder(BrandRequest.class)
          .set("name", "새브랜드")
          .sample();

      String body = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(post("/brands")
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isCreated())                 // 201 응답
          .andExpect(jsonPath("$").value(notNullValue())); // 반환된 id가 null이 아닌지 검사
    }

    @Test
    void 빈문자열로_브랜드를_등록하면_400상태가_반환된다() throws Exception {
      // given
      BrandRequest request = FixtureUtils.monkey.giveMeOne(BrandRequest.class);
      ReflectionTestUtils.setField(request, "name", "");
      
      String body = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(post("/brands")
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isBadRequest()); // @NotBlank 검사 실패
    }

    @Test
    void 브랜드명을_1글자로_등록하면_400상태가_반환된다() throws Exception {
      // given
      BrandRequest request = FixtureUtils.monkey.giveMeOne(BrandRequest.class);
      ReflectionTestUtils.setField(request, "name", "A");
      String body = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(post("/brands")
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class 브랜드_수정_API {

    @Test
    void 존재하지_않는_ID로_브랜드를_수정하면_404상태가_반환된다() throws Exception {
      // given
      BrandRequest request = FixtureUtils.monkey.giveMeBuilder(BrandRequest.class)
          .set("name", "수정브랜드")
          .sample();
      String body = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/brands/{id}", 9999)
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isNotFound());
    }

    @Test
    void id가_0이하인_경우_400상태가_반환된다() throws Exception {
      // given
      BrandRequest request = FixtureUtils.monkey.giveMeBuilder(BrandRequest.class)
          .set("name", "수정브랜드")
          .sample();
      String body = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/brands/{id}", 0)
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isBadRequest());
    }

    @Test
    void 브랜드명을_공백으로_수정하면_400상태가_반환된다() throws Exception {
      // given
      BrandRequest request = FixtureUtils.monkey.giveMeOne(BrandRequest.class);
      ReflectionTestUtils.setField(request, "name", "");
      String body = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/brands/{id}", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isBadRequest());
    }

    @Test
    void 정상적으로_브랜드를_수정하면_200상태와_id가_반환된다() throws Exception {
      // given
      BrandRequest request = FixtureUtils.monkey.giveMeBuilder(BrandRequest.class)
          .set("name", "수정브랜드")
          .sample();
      String body = objectMapper.writeValueAsString(request);

      // when & then
      mockMvc.perform(put("/brands/{id}", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").value(1));
    }
  }

  @Nested
  class 브랜드_삭제_API {

    @Test
    void 존재하지_않는_ID로_삭제하면_404상태가_반환된다() throws Exception {
      // given
      // when & then
      mockMvc.perform(delete("/brands/{id}", 9999))
          .andExpect(status().isNotFound());
    }

    @Test
    void id가_0이하인_경우_삭제하면_400상태가_반환된다() throws Exception {
      // when & then
      mockMvc.perform(delete("/brands/{id}", 0))
          .andExpect(status().isBadRequest());
    }

    @Test
    void 브랜드를_정상적으로_삭제하면_200상태와_ID가_반환된다() throws Exception {
      // given
      // when & then
      mockMvc.perform(delete("/brands/{id}", 1))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").value(1));
    }
  }
}
