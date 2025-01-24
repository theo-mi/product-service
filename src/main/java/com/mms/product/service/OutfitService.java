package com.mms.product.service;

import com.mms.product.model.Category;
import com.mms.product.model.Outfit;
import com.mms.product.model.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutfitService {

  private final CategoryService categoryService;
  private final ProductService productService;

  /**
   * 가장 저렴한 옷 조합을 조회한다.
   */
  public Outfit getCheapestOutfit() {
    final List<Category> categories = categoryService.getAllCategories();

    Outfit outfit = Outfit.of();
    categories.forEach(category -> {
      Product cheapestProductByCategory = productService.getCheapestByCategory(category);
      outfit.add(category, cheapestProductByCategory);
    });

    return outfit;
  }
}
