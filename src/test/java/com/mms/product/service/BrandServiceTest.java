package com.mms.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

import com.mms.product.exception.NotFoundException;
import com.mms.product.model.dto.CheapestBrandDto;
import com.mms.product.model.entity.Brand;
import com.mms.product.model.entity.Category;
import com.mms.product.repository.BrandRepository;
import com.mms.product.utils.FixtureUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

  @InjectMocks
  private BrandService brandService;
  
  @Mock
  private BrandRepository brandRepository;

  @Test
  void 모든_브랜드를_조회하면_브랜드_목록이_반환된다() {
    // given
    List<Brand> expectedBrands = FixtureUtils.monkey.giveMe(Brand.class, 5);
    when(brandRepository.findAll()).thenReturn(expectedBrands);

    // when
    List<Brand> actualBrands = brandService.getAllBrands();

    // then
    assertThat(actualBrands).isEqualTo(expectedBrands);
  }

  @Test
  void id로_브랜드를_조회하면_해당_브랜드가_반환된다() {
    // given
    Brand expectedBrand = FixtureUtils.monkey.giveMeOne(Brand.class);
    when(brandRepository.findById(anyLong())).thenReturn(Optional.of(expectedBrand));

    // when
    Brand actualBrand = brandService.getById(expectedBrand.getId());

    // then
    assertThat(actualBrand).isEqualTo(expectedBrand);
  }

  @Test
  void 존재하지_않는_id로_브랜드를_조회하면_예외가_발생한다() {
    // given
    Long nonExistentBrandId = 999L;
    when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> brandService.getById(nonExistentBrandId))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("해당 브랜드가 존재하지 않습니다.");
  }

  @Test
  void 브랜드를_추가하면_추가된_브랜드의_id가_반환된다() {
    // given
    Brand newBrand = FixtureUtils.monkey.giveMeOne(Brand.class);
    when(brandRepository.save(any(Brand.class))).thenReturn(newBrand);

    // when
    Long brandId = brandService.add(newBrand.getName());

    // then
    assertThat(brandId).isEqualTo(newBrand.getId());
  }

  @Test
  void 브랜드를_수정하면_수정된_브랜드의_id가_반환된다() {
    // given
    Brand existingBrand = FixtureUtils.monkey.giveMeOne(Brand.class);
    when(brandRepository.findById(anyLong())).thenReturn(Optional.of(existingBrand));

    // when
    Long updatedBrandId = brandService.update(existingBrand.getId(), "수정된 브랜드명");

    // then
    assertThat(updatedBrandId).isEqualTo(existingBrand.getId());
  }

  @Test
  void 존재하지_않는_id로_브랜드를_수정하면_예외가_발생한다() {
    // given
    Long nonExistentBrandId = 999L;
    when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> brandService.update(nonExistentBrandId, "수정된 브랜드명"))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("해당 브랜드가 존재하지 않습니다.");
  }

  @Test
  void 브랜드를_삭제하면_삭제된_브랜드의_id가_반환된다() {
    // given
    Brand existingBrand = FixtureUtils.monkey.giveMeOne(Brand.class);
    when(brandRepository.findById(anyLong())).thenReturn(Optional.of(existingBrand));

    // when
    Long deletedBrandId = brandService.delete(existingBrand.getId());

    // then
    assertThat(deletedBrandId).isEqualTo(existingBrand.getId());
  }

  @Test
  void 존재하지_않는_id로_브랜드를_삭제하면_예외가_발생한다() {
    // given
    Long nonExistentBrandId = 999L;
    when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> brandService.delete(nonExistentBrandId))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("해당 브랜드가 존재하지 않습니다.");
  }

  @Test
  void 카테고리에_속한_상품_중_가장_저렴한_브랜드를_조회하면_브랜드가_반환된다() {
    // given
    List<Category> categories = FixtureUtils.monkey.giveMe(Category.class, 3);
    Brand cheapestBrand = FixtureUtils.monkey.giveMeOne(Brand.class);
    CheapestBrandDto cheapestBrandDto = new CheapestBrandDto(cheapestBrand.getId(), BigDecimal.valueOf(1000));

    when(brandRepository.findCheapestBrandWithCategories(anyList())).thenReturn(Optional.of(cheapestBrandDto));
    when(brandRepository.findById(anyLong())).thenReturn(Optional.of(cheapestBrand));

    // when
    Brand actualBrand = brandService.findCheapestBrandWithCategories(categories);

    // then
    assertThat(actualBrand).isEqualTo(cheapestBrand);
  }

  @Test
  void 카테고리에_속한_상품_중_가장_저렴한_브랜드가_없으면_예외가_발생한다() {
    // given
    List<Category> categories = FixtureUtils.monkey.giveMe(Category.class, 3);
    when(brandRepository.findCheapestBrandWithCategories(anyList())).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> brandService.findCheapestBrandWithCategories(categories))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("최저가 코디를 위한 브랜드가 존재하지 않습니다.");
  }
}