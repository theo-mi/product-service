package com.mms.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.mms.product.exception.NotFoundException;
import com.mms.product.model.entity.Category;
import com.mms.product.repository.CategoryRepository;
import com.mms.product.utils.FixtureUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @InjectMocks
  private CategoryService categoryService;
  
  @Mock
  private CategoryRepository categoryRepository;

  @Test
  void 모든_카테고리를_조회하면_카테고리_목록이_반환된다() {
    // given
    List<Category> expectedCategories = FixtureUtils.monkey.giveMe(Category.class, 5);
    when(categoryRepository.findAll()).thenReturn(expectedCategories);

    // when
    List<Category> actualCategories = categoryService.getAllCategories();

    // then
    assertThat(actualCategories).isEqualTo(expectedCategories);
  }

  @Test
  void 이름으로_카테고리를_조회하면_해당_카테고리가_반환된다() {
    // given
    Category expectedCategory = FixtureUtils.monkey.giveMeOne(Category.class);
    when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(expectedCategory));

    // when
    Category actualCategory = categoryService.getByName(expectedCategory.getName());

    // then
    assertThat(actualCategory).isEqualTo(expectedCategory);
  }

  @Test
  void 존재하지_않는_이름으로_카테고리를_조회하면_예외가_발생한다() {
    // given
    String nonExistentCategoryName = "없는 카테고리";
    when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> categoryService.getByName(nonExistentCategoryName))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("해당 카테고리가 존재하지 않습니다.");
  }

  @Test
  void id로_카테고리를_조회하면_해당_카테고리가_반환된다() {
    // given
    Category expectedCategory = FixtureUtils.monkey.giveMeOne(Category.class);
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(expectedCategory));

    // when
    Category actualCategory = categoryService.getById(expectedCategory.getId());

    // then
    assertThat(actualCategory).isEqualTo(expectedCategory);
  }

  @Test
  void 존재하지_않는_id로_카테고리를_조회하면_예외가_발생한다() {
    // given
    Long nonExistentCategoryId = 999L;
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> categoryService.getById(nonExistentCategoryId))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("해당 카테고리가 존재하지 않습니다.");
  }
}