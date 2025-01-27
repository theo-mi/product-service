package com.mms.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import com.mms.product.exception.NotFoundException;
import com.mms.product.model.entity.Brand;
import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import com.mms.product.repository.ProductRepository;
import com.mms.product.utils.FixtureUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * ProductService 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @InjectMocks
  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryService categoryService;

  @Mock
  private BrandService brandService;

  private Category mockCategory;
  private Brand mockBrand;
  private Product mockProduct;

  @BeforeEach
  void setUp() {
    // Fixture 설정
    mockCategory = FixtureUtils.monkey.giveMeBuilder(Category.class)
        .set("id", 100L)
        .sample();

    mockBrand = FixtureUtils.monkey.giveMeBuilder(Brand.class)
        .set("id", 200L)
        .sample();

    mockProduct = FixtureUtils.monkey.giveMeBuilder(Product.class)
        .set("category", mockCategory)
        .set("brand", mockBrand)
        .set("price", BigDecimal.valueOf(9999))
        .sample();
  }

  /**
   * getCheapestByCategory
   */
  @Test
  void 카테고리ID로_최저가상품을_조회하면_상품목록이_반환된다() {
    // given
    var productList = FixtureUtils.monkey.giveMeBuilder(Product.class)
        .set("category", mockCategory)
        .sampleList(3);

    given(productRepository.findMinPriceProductsByCategory(mockCategory.getId()))
        .willReturn(productList);

    // when
    List<Product> result = productService.getCheapestByCategory(mockCategory);

    // then
    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(3);
    assertThat(result.get(0).getCategory()).isEqualTo(mockCategory);
  }

  @Test
  void 카테고리ID로_최저가상품을_조회했는데_결과가_없으면_예외가_발생한다() {
    // given
    given(productRepository.findMinPriceProductsByCategory(mockCategory.getId()))
        .willReturn(List.of());

    // when & then
    assertThatThrownBy(() -> productService.getCheapestByCategory(mockCategory))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("코디가 불가능합니다");
  }

  /**
   * getExpensiveByCategory
   */
  @Test
  void 카테고리ID로_최고가상품을_조회하면_상품목록이_반환된다() {
    // given
    var productList = FixtureUtils.monkey.giveMeBuilder(Product.class)
        .set("category", mockCategory)
        .sampleList(2);

    given(productRepository.findMaxPriceProductsByCategory(mockCategory.getId()))
        .willReturn(productList);

    // when
    List<Product> result = productService.getExpensiveByCategory(mockCategory);

    // then
    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getCategory()).isEqualTo(mockCategory);
  }

  @Test
  void 카테고리ID로_최고가상품을_조회했는데_결과가_없으면_예외가_발생한다() {
    // given
    given(productRepository.findMaxPriceProductsByCategory(mockCategory.getId()))
        .willReturn(List.of());

    // when & then
    assertThatThrownBy(() -> productService.getExpensiveByCategory(mockCategory))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("코디가 불가능합니다");
  }

  /**
   * getCheapestBy
   */
  @Test
  void 카테고리와_브랜드ID로_최저가상품을_조회하면_여러개중_첫번째가_반환된다() {
    // given
    var cheapestProducts = FixtureUtils.monkey.giveMeBuilder(Product.class)
        .set("brand", mockBrand)
        .set("category", mockCategory)
        .sampleList(3);

    given(productRepository.findMinPriceProductByBrandAndCategory(mockCategory.getId(), mockBrand.getId()))
        .willReturn(cheapestProducts);

    // when
    Product result = productService.getCheapestBy(mockBrand, mockCategory);

    // then
    assertThat(result).isEqualTo(cheapestProducts.get(0));
    assertThat(result.getBrand()).isEqualTo(mockBrand);
    assertThat(result.getCategory()).isEqualTo(mockCategory);
  }

  @Test
  void 카테고리와_브랜드ID로_최저가상품을_조회했는데_결과가_없으면_예외가_발생한다() {
    // given
    given(productRepository.findMinPriceProductByBrandAndCategory(mockCategory.getId(), mockBrand.getId()))
        .willReturn(List.of());

    // when & then
    assertThatThrownBy(() -> productService.getCheapestBy(mockBrand, mockCategory))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("코디가 불가능합니다");
  }

  /**
   * add
   */
  @Test
  void 상품을_추가하면_DB에_저장되고_새로운_ID가_반환된다() {
    // given
    var newPrice = BigDecimal.valueOf(5555);
    given(categoryService.getById(mockCategory.getId())).willReturn(mockCategory);
    given(brandService.getById(mockBrand.getId())).willReturn(mockBrand);

    Product savedProduct = FixtureUtils.monkey.giveMeBuilder(Product.class)
        .set("id", 999L)
        .sample();
    savedProduct.update(mockCategory, mockBrand, newPrice);

    given(productRepository.save(ArgumentMatchers.any(Product.class)))
        .willReturn(savedProduct);

    // when
    Long productId = productService.add(mockCategory.getId(), mockBrand.getId(), newPrice);

    // then
    assertThat(productId).isEqualTo(999L);
  }

  /**
   * update
   */
  @Test
  void 존재하는_상품ID로_수정하면_상품정보가_갱신된다() {
    // given
    BigDecimal newPrice = BigDecimal.valueOf(8888);
    given(productRepository.findById(mockProduct.getId()))
        .willReturn(Optional.of(mockProduct));
    given(categoryService.getById(mockCategory.getId())).willReturn(mockCategory);
    given(brandService.getById(mockBrand.getId())).willReturn(mockBrand);

    // when
    Long updatedId = productService.update(mockProduct.getId(), mockCategory.getId(), mockBrand.getId(), newPrice);

    // then
    assertThat(updatedId).isEqualTo(mockProduct.getId());
    assertThat(mockProduct.getPrice()).isEqualTo(newPrice);
  }

  @Test
  void 존재하지_않는_상품ID로_수정하면_예외가_발생한다() {
    // given
    Long invalidProductId = 99999L;
    given(productRepository.findById(invalidProductId))
        .willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() ->
        productService.update(invalidProductId, mockCategory.getId(), mockBrand.getId(), BigDecimal.ONE)
    ).isInstanceOf(NotFoundException.class)
        .hasMessageContaining("해당 상품이 존재하지 않습니다");
  }

  /**
   * delete
   */
  @Test
  void 존재하는_상품ID를_삭제하면_상품이_삭제되고_ID가_반환된다() {
    // given
    given(productRepository.findById(mockProduct.getId()))
        .willReturn(Optional.of(mockProduct));
    willDoNothing().given(productRepository).deleteById(mockProduct.getId());

    // when
    Long deletedId = productService.delete(mockProduct.getId());

    // then
    assertThat(deletedId).isEqualTo(mockProduct.getId());
  }

  @Test
  void 존재하지_않는_상품ID를_삭제하면_예외가_발생한다() {
    // given
    Long invalidId = 55555L;
    given(productRepository.findById(invalidId))
        .willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> productService.delete(invalidId))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("해당 상품이 존재하지 않습니다");
  }
}
