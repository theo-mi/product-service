package com.mms.product.model.request;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class ProductRequest {
  private Long categoryId;
  
  private Long brandId;

  private BigDecimal price;
}
