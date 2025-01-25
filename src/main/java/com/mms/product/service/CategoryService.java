package com.mms.product.service;

import com.mms.product.model.Category;
import com.mms.product.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  /**
   * 이름으로 카테고리를 조회한다.
   *
   * @param name 카테고리 이름
   * @return 카테고리
   */
  public Category getByName(String name) {
    return categoryRepository.findByName(name);
  }
}
