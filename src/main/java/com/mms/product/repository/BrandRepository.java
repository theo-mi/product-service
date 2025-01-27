package com.mms.product.repository;

import com.mms.product.model.dto.CheapestBrandDto;
import com.mms.product.model.entity.Brand;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

  @Query(value = """
      WITH brand_category_min_price AS (
          SELECT
              p.brand_id         AS brand_id,
              p.category_id      AS category_id,
              MIN(p.price)       AS min_price
          FROM product p
          WHERE p.category_id IN (:categoryIds)
          GROUP BY p.brand_id, p.category_id
      )
      SELECT
          bcmp.brand_id       AS brandId,
          SUM(bcmp.min_price) AS totalPrice
      FROM brand_category_min_price bcmp
      GROUP BY bcmp.brand_id
      HAVING COUNT(bcmp.category_id) = (
        SELECT COUNT(c.id)
        FROM category c
        WHERE c.id IN (:categoryIds)
      )
      ORDER BY SUM(bcmp.min_price)
      LIMIT 1
      """,
      nativeQuery = true)
  Optional<CheapestBrandDto> findCheapestBrandWithCategories(@Param("categoryIds") List<Long> categoryIds);
}
