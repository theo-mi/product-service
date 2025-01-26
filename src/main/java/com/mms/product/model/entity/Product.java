package com.mms.product.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("상품 ID")
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  
  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @Comment("가격")
  private BigDecimal price;

  @Builder(access = AccessLevel.PRIVATE)
  public Product(Long id, Category category, Brand brand, BigDecimal price) {
    this.id = id;
    this.category = category;
    this.brand = brand;
    this.price = price;
  }

  public static Product of(Category category, Brand brand, BigDecimal price) {
    return Product.builder()
        .category(category)
        .category(category)
        .brand(brand)
        .price(price)
        .build();
  }

  /**
   * 상품 정보를 수정한다.
   *
   * @param category 카테고리
   * @param brand    브랜드
   * @param price    가격
   */
  public void update(Category category, Brand brand, BigDecimal price) {
    this.category = category;
    this.brand = brand;
    this.price = price;
  }
}
