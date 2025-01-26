package com.mms.product.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "브랜드 등록시 요청하는 정보")
@Getter
public class BrandRequest {

  @Schema(description = "브랜드명", example = "나이키")
  private String name;
}
