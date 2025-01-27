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
      WITH selected_category_count AS (
            SELECT COUNT(c.id) AS total
          FROM category c
          WHERE c.id IN (:categoryIds)
      ),
        brand_min_prices AS (
            SELECT\s
                p.brand_id    AS brand_id,
                p.category_id AS category_id,
                MIN(p.price)  AS min_price
          FROM product p
          WHERE p.category_id IN (:categoryIds)
              AND p.deleted_at IS NULL
          GROUP BY p.brand_id, p.category_id
      ),
        brand_aggregates AS (
            SELECT\s
                bmp.brand_id              AS brand_id,
                SUM(bmp.min_price)        AS total_price,
                COUNT(bmp.category_id)    AS covered
            FROM brand_min_prices bmp
            JOIN brand b ON b.id = bmp.brand_id
                        AND b.deleted_at IS NULL
            GROUP BY bmp.brand_id
      )
        SELECT\s
            ba.brand_id    AS brandId,
            ba.total_price AS totalPrice
        FROM brand_aggregates ba
        JOIN selected_category_count scc
            ON ba.covered = scc.total
        ORDER BY ba.total_price
        LIMIT 1
      """,
      nativeQuery = true)
  Optional<CheapestBrandDto> findCheapestBrandWithCategories(@Param("categoryIds") List<Long> categoryIds);
}
