package com.mms.product.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Schema(description = "브랜드 등록시 요청하는 정보")
@Getter
public class BrandRequest {

  @Schema(description = "브랜드명", example = "나이키")
  @NotBlank(message = "브랜드명은 필수입니다.")
  @Size(min = 2, max = 100, message = "브랜드명은 100자 이하여야 합니다.")
  private String name;
}
