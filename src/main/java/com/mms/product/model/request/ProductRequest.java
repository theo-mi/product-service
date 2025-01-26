package com.mms.product.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;

@Schema(description = "상품 등록시 요청하는 정보")
@Getter
public class ProductRequest {

  @Schema(description = "카테고리 ID", example = "1")
  private Long categoryId;

  @Schema(description = "브랜드 ID", example = "1")
  private Long brandId;

  @Schema(description = "상품가격", example = "52300")
  private BigDecimal price;
}
