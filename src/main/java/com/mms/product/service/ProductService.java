package com.mms.product.service;

import com.mms.product.exception.NotFoundException;
import com.mms.product.model.entity.Brand;
import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import com.mms.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  private final CategoryService categoryService;
  private final BrandService brandService;

  /**
   * 카테고리에서 최저가 상품을 조회한다.
   *
   * @param category 카테고리
   * @return 최저가 상품
   */
  public Product getCheapestByCategory(Category category) {
    final List<Product> cheapestProductByCategory = productRepository.findMinPriceProductsByCategory(category.getId());

    if (cheapestProductByCategory.isEmpty()) {
      throw new NotFoundException(String.format("해당 카테고리에 상품이 존재하지 않아 코디가 불가능합니다. (category: %s)", category.getName()));
    }

    // 중복된 최저가 상품이 있는 경우 마지막 상품을 반환한다. (예시에 따라 수정)
    // 현재는 별다른 정책이 없기 때문에 limit 1으로 가져오도록 수정하면 조회 성능이 더 좋아질듯.
    return cheapestProductByCategory.getLast();
  }

  /**
   * 카테고리에서 최고가 상품을 조회한다.
   *
   * @param category 카테고리
   * @return 최저가 상품
   */
  public Product getExpensiveByCategory(Category category) {
    final List<Product> expensiveProductByCategory = productRepository.findMaxPriceProductsByCategory(category.getId());

    if (expensiveProductByCategory.isEmpty()) {
      throw new NotFoundException(String.format("해당 카테고리에 상품이 존재하지 않아 코디가 불가능합니다. (category: %s)", category.getName()));
    }

    // 중복된 최저가 상품이 있는 경우 마지막 상품을 반환한다. (예시에 따라 수정)
    // 현재는 별다른 정책이 없기 때문에 limit 1으로 가져오도록 수정하면 조회 성능이 더 좋아질듯.
    return expensiveProductByCategory.getLast();
  }

  /**
   * 카테고리와 브랜드에 해당하는 최저가 상품을 조회한다.
   *
   * @param brand    브랜드
   * @param category 카테고리
   * @return 최저가 상품
   */
  public Product getCheapestBy(Brand brand, Category category) {
    final List<Product> cheapestProductByBrandAndCategory = productRepository.findMinPriceProductByBrandAndCategory(category.getId(), brand.getId());

    if (cheapestProductByBrandAndCategory.isEmpty()) {
      throw new NotFoundException(String.format("해당 카테고리에 상품이 존재하지 않아 코디가 불가능합니다. (category: %s)", category.getName()));
    }

    return cheapestProductByBrandAndCategory.getLast();
  }

  /**
   * 상품을 추가한다.
   *
   * @param categoryId 카테고리 id
   * @param brandId    브랜드 id
   * @param price      가격
   * @return 추가된 상품의 id
   */
  @Transactional
  public Long add(Long categoryId, Long brandId, BigDecimal price) {
    final Category category = categoryService.getById(categoryId);
    final Brand brand = brandService.getById(brandId);

    final Product product = productRepository.save(Product.of(category, brand, price));

    return product.getId();
  }

  /**
   * 상품을 수정한다.
   *
   * @param productId  상품의 id
   * @param categoryId 카테고리 id
   * @param brandId    브랜드 id
   * @param price      가격
   * @return 수정된 상품의 id
   */
  @Transactional
  public Long update(Long productId, Long categoryId, Long brandId, BigDecimal price) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException(String.format("해당 상품이 존재하지 않습니다. (id: %d)", productId)));

    final Category category = categoryService.getById(categoryId);
    final Brand brand = brandService.getById(brandId);

    product.update(category, brand, price);

    return productId;
  }

  /**
   * 상품을 삭제한다.
   *
   * @param id 상품 id
   * @return 삭제된 상품 id
   */
  @Transactional
  public Long delete(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format("해당 상품이 존재하지 않습니다. (id: %d)", id)));

    productRepository.deleteById(product.getId());

    return id;
  }
}
