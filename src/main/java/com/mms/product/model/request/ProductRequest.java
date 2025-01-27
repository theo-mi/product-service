package com.mms.product.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Getter;

@Schema(description = "상품 등록시 요청하는 정보")
@Getter
public class ProductRequest {

  @Schema(description = "카테고리 ID", example = "1")
  @NotNull(message = "카테고리 ID는 필수입니다.")
  @Positive(message = "카테고리 ID는 1 이상이어야 합니다.")
  private Long categoryId;

  @Schema(description = "브랜드 ID", example = "1")
  @NotNull(message = "카테고리 ID는 필수입니다.")
  @Positive(message = "브랜드 ID는 1 이상이어야 합니다.")
  private Long brandId;

  @Schema(description = "상품가격", example = "52300")
  @NotNull(message = "카테고리 ID는 필수입니다.")
  @Min(value = 1, message = "상품 가격은 1원 이상이어야 합니다.")
  private BigDecimal price;
}
