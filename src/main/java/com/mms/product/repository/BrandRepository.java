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
      WITH category_count AS (
          SELECT COUNT(c.id) AS total_categories
          FROM category c
          WHERE c.id IN (:categoryIds)
      ),
      brand_category_min_price AS (
          SELECT
              p.brand_id         AS brand_id,
              p.category_id      AS category_id,
              MIN(p.price)       AS min_price
          FROM product p
          WHERE p.category_id IN (:categoryIds)
          GROUP BY p.brand_id, p.category_id
      ),
      brand_total_price AS (
          SELECT
              bcmp.brand_id       AS brand_id,
              SUM(bcmp.min_price) AS total_price,
              COUNT(bcmp.category_id) AS covered_categories
          FROM brand_category_min_price bcmp
          GROUP BY bcmp.brand_id
      )
      SELECT
          btp.brand_id       AS brandId,
          btp.total_price    AS totalPrice
      FROM brand_total_price btp
      JOIN category_count cc
        ON btp.covered_categories = cc.total_categories
      ORDER BY btp.total_price
      LIMIT 1;
      """,
      nativeQuery = true)
  Optional<CheapestBrandDto> findCheapestBrandWithCategories(@Param("categoryIds") List<Long> categoryIds);
}
