package com.mms.product.service;

import com.mms.product.enums.exception.NotFoundErrorFormat;
import com.mms.product.model.entity.Category;
import com.mms.product.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  /**
   * 모든 카테고리를 조회한다.
   *
   * @return 카테고리 목록
   */
  @Cacheable(value = "categories")
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  /**
   * 카테고리명으로 카테고리를 조회한다.
   *
   * @param name 카테고리명
   * @return 카테고리
   */
  @Cacheable(value = "category", key = "'name:' + #name")
  public Category getByName(String name) {
    return categoryRepository.findByName(name)
        .orElseThrow(() -> NotFoundErrorFormat.CATEGORY_NAME.toException(name));

  }
  
  /**
   * id로 카테고리를 조회한다.
   *
   * @param id
   * @return
   */
  @Cacheable(value = "category", key = "'id:' + #id")
  public Category getById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> NotFoundErrorFormat.CATEGORY_ID.toException(id));

  }
}
