package com.mms.product.service;

import com.mms.product.model.Brand;
import com.mms.product.model.Category;
import com.mms.product.model.Product;
import com.mms.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  /**
   * 카테고리에서 최저가 상품을 조회한다.
   *
   * @param category 카테고리
   * @return 최저가 상품
   */
  public Product getCheapestByCategory(Category category) {
    final List<Product> cheapestProductByCategory = productRepository.findMinPriceProductsByCategory(category.getId());

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
    final List<Product> minPriceProductByBrandAndCategory = productRepository.findMinPriceProductByBrandAndCategory(category.getId(), brand.getId());

    return minPriceProductByBrandAndCategory.getLast();
  }


}
