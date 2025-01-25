package com.mms.product.service;

import com.mms.product.model.Brand;
import com.mms.product.repository.BrandRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepository;

  /**
   * 모든 브랜드를 조회한다.
   *
   * @return 브랜드 목록
   */
  public List<Brand> getAllBrands() {
    return brandRepository.findAll();
  }
}
