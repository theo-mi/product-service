package com.mms.product.service;

import com.mms.product.model.entity.Brand;
import com.mms.product.repository.BrandRepository;
import jakarta.transaction.Transactional;
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

  /**
   * id로 브랜드를 조회한다.
   *
   * @param id 브랜드 id
   * @return 브랜드
   */
  public Brand getById(Long id) {
    return brandRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(String.format("해당 브랜드가 존재하지 않습니다. (id: %d)", id)));
  }

  /**
   * 브랜드를 추가한다.
   *
   * @param name 브랜드 이름
   * @return 추가된 브랜드의 id
   */
  @Transactional
  public Long add(String name) {
    Brand brand = brandRepository.save(Brand.of(name));

    return brand.getId();
  }

  /**
   * 브랜드를 수정한다.
   *
   * @param id   브랜드 id
   * @param name 브랜드 이름
   * @return 수정된 브랜드의 id
   */
  @Transactional
  public Long update(Long id, String name) {
    Brand brand = brandRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(String.format("해당 브랜드가 존재하지 않습니다. (id: %d)", id)));

    brand.updateName(name);

    return id;
  }

  /**
   * 브랜드를 삭제한다.
   *
   * @param id 브랜드 id
   * @return 삭제된 브랜드의 id
   */
  public Long delete(Long id) {
    brandRepository.deleteById(id);

    return id;
  }
}
