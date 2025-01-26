package com.mms.product.repository;

import com.mms.product.model.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  /**
   * 카테고리에서 최저가 상품을 조회한다.
   *
   * @param categoryId 카테고리 ID
   * @return 최저가 상품 목록
   */
  @Query("SELECT p "
      + "FROM Product p "
      + "WHERE p.category.id = :categoryId "
      + "AND p.price = (SELECT MIN(p2.price) FROM Product p2 WHERE p2.category.id = :categoryId)")
  List<Product> findMinPriceProductsByCategory(Long categoryId);

  /**
   * 카테고리에서 최고가 상품을 조회한다.
   *
   * @param categoryId 카테고리 ID
   * @return 최저가 상품 목록
   */
  @Query("SELECT p "
      + "FROM Product p "
      + "WHERE p.category.id = :categoryId "
      + "AND p.price = (SELECT MAX(p2.price) FROM Product p2 WHERE p2.category.id = :categoryId)")
  List<Product> findMaxPriceProductsByCategory(Long categoryId);

  /**
   * 카테고리와 브랜드에 해당하는 최저가 상품을 조회한다.
   *
   * @param categoryId 카테고리 ID
   * @param brandId    브랜드 ID
   * @return 최저가 상품 목록
   */
  @Query("SELECT p "
      + "FROM Product p "
      + "WHERE p.category.id = :categoryId "
      + "AND p.brand.id = :brandId "
      + "AND p.price = (SELECT MIN(p2.price) FROM Product p2 WHERE p2.category.id = :categoryId AND p2.brand.id = :brandId)")
  List<Product> findMinPriceProductByBrandAndCategory(Long categoryId, Long brandId);
}
