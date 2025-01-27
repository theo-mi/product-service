package com.mms.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mms.product.exception.NotFoundException;
import com.mms.product.model.Outfit;
import com.mms.product.model.entity.Brand;
import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import com.mms.product.utils.FixtureUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OutfitServiceTest {

  @InjectMocks
  private OutfitService outfitService;

  @Mock
  private BrandService brandService;

  @Mock
  private CategoryService categoryService;

  @Mock
  private ProductService productService;

  @Test
  void 가장_저렴한_옷_조합을_조회하면_옷_조합이_반환된다() {
    // given
    List<Category> categories = FixtureUtils.monkey.giveMe(Category.class, 3);
    when(categoryService.getAllCategories()).thenReturn(categories);

    List<Product> cheapestProducts = FixtureUtils.monkey.giveMe(Product.class, 3);
    when(productService.getCheapestByCategory(any(Category.class))).thenReturn(cheapestProducts);

    // when
    Outfit outfit = outfitService.getCheapestOutfit();

    // then
    assertThat(outfit).isNotNull();
    assertThat(outfit.getOutfit().size()).isEqualTo(categories.size());
  }

  @Test
  void 가장_저렴한_옷_조합을_조회했는데_카테고리가_없으면_예외가_발생한다() {
    // given
    when(categoryService.getAllCategories()).thenReturn(List.of());

    // when & then
    assertThatThrownBy(() -> outfitService.getCheapestOutfit())
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("코디가 불가능합니다");
  }

  @Test
  void 가장_저렴한_브랜드의_옷_조합을_조회하면_옷_조합이_반환된다() {
    // given
    List<Category> categories = FixtureUtils.monkey.giveMe(Category.class, 3);
    when(categoryService.getAllCategories()).thenReturn(categories);

    Brand cheapestBrand = FixtureUtils.monkey.giveMeOne(Brand.class);
    when(brandService.findCheapestBrandWithCategories(categories)).thenReturn(cheapestBrand);

    Product cheapestProduct = FixtureUtils.monkey.giveMeOne(Product.class);
    when(productService.getCheapestBy(any(Brand.class), any(Category.class))).thenReturn(cheapestProduct);

    // when
    Outfit outfit = outfitService.getCheapestOutfitByBrand();

    // then
    assertThat(outfit).isNotNull();
    assertThat(outfit.getOutfit().size()).isEqualTo(categories.size());
  }
}