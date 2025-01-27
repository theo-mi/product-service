package com.mms.product.service;

import com.mms.product.enums.exception.NotFoundErrorFormat;
import com.mms.product.exception.NotFoundException;
import com.mms.product.model.dto.CheapestBrandDto;
import com.mms.product.model.entity.Brand;
import com.mms.product.model.entity.Category;
import com.mms.product.repository.BrandRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
        .orElseThrow(() -> NotFoundErrorFormat.BRAND_ID.toException(id));

  }

  /**
   * 브랜드를 추가한다.
   *
   * @param name 브랜드명
   * @return 추가된 브랜드의 id
   */
  @Transactional
  @CacheEvict(value = {"cheapestOutfit", "cheapestOutfitByBrand"}, allEntries = true)
  public Long add(String name) {
    Brand brand = brandRepository.save(Brand.of(name));

    return brand.getId();
  }

  /**
   * 브랜드를 수정한다.
   *
   * @param id   브랜드 id
   * @param name 브랜드명
   * @return 수정된 브랜드의 id
   */
  @Transactional
  @CacheEvict(value = {"cheapestOutfit", "cheapestOutfitByBrand"}, allEntries = true)
  public Long update(Long id, String name) {
    Brand brand = brandRepository.findById(id)
        .orElseThrow(() -> NotFoundErrorFormat.BRAND_ID.toException(id));

    brand.updateName(name);

    return id;
  }

  /**
   * 브랜드를 삭제한다.
   *
   * @param id 브랜드 id
   * @return 삭제된 브랜드의 id
   */
  @Transactional
  @CacheEvict(value = {"cheapestOutfit", "cheapestOutfitByBrand"}, allEntries = true)
  public Long delete(Long id) {
    Brand brand = brandRepository.findById(id)
        .orElseThrow(() -> NotFoundErrorFormat.BRAND_ID.toException(id));

    brandRepository.deleteById(brand.getId());

    return id;
  }

  /**
   * 카테고리에 속한 상품 중 가장 저렴한 브랜드를 조회한다.
   *
   * @param categories 카테고리 목록
   * @return 가장 저렴한 브랜드
   */
  public Brand findCheapestBrandWithCategories(List<Category> categories) {
    List<Long> categoryIds = categories.stream()
        .map(Category::getId)
        .toList();

    CheapestBrandDto cheapestBrandDto = brandRepository.findCheapestBrandWithCategories(categoryIds)
        .orElseThrow(() -> new NotFoundException("최저가 코디를 위한 브랜드가 존재하지 않습니다."));

    return getById(cheapestBrandDto.brandId());
  }
}
