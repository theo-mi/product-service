package com.mms.product.service;

import com.mms.product.model.Outfit;
import com.mms.product.model.entity.Brand;
import com.mms.product.model.entity.Category;
import com.mms.product.model.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutfitService {

  private final BrandService brandService;
  private final CategoryService categoryService;
  private final ProductService productService;

  /**
   * 가장 저렴한 옷 조합을 조회한다.
   */
  @Cacheable(value = "cheapestOutfit")
  public Outfit getCheapestOutfit() {
    final List<Category> categories = categoryService.getAllCategories();

    Outfit outfit = Outfit.of();
    categories.forEach(category -> {
      Product cheapestProductByCategory = productService.getCheapestByCategory(category);
      outfit.add(category, cheapestProductByCategory);
    });

    return outfit;
  }

  /**
   * 가장 저렴한 브랜드의 옷 조합을 조회한다.
   *
   * @return 가장 저렴한 브랜드의 옷 조합
   */
  @Cacheable(value = "cheapestOutfitByBrand")
  public Outfit getCheapestOutfitByBrand() {
    final List<Category> categories = categoryService.getAllCategories();

    Brand cheapestBrand = brandService.findCheapestBrandWithCategories(categories);

    Outfit cheapestBrandOutfit = Outfit.of();
    for (Category category : categories) {
      Product cheapestProductByBrand = productService.getCheapestBy(cheapestBrand, category);
      cheapestBrandOutfit.add(category, cheapestProductByBrand);
      }

    return cheapestBrandOutfit;
  }
}
