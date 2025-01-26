package com.mms.product.service;

import com.mms.product.exception.NotFoundException;
import com.mms.product.model.entity.Category;
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
   * 카테고리명으로 카테고리를 조회한다.
   *
   * @param name 카테고리명
   * @return 카테고리
   */
  public Category getByName(String name) {
    return categoryRepository.findByName(name)
        .orElseThrow(() -> new NotFoundException(String.format("해당 카테고리가 존재하지 않습니다. (name: %s)", name)));
  }
  
  /**
   * id로 카테고리를 조회한다.
   *
   * @param id
   * @return
   */
  public Category getById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format("해당 카테고리가 존재하지 않습니다. (id: %d)", id)));
  }
}
