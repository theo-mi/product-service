package com.mms.product.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE brand SET deleted_at = now() WHERE id = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Positive
  @Comment("브랜드 ID")
  private Long id;

  @Size(min = 1, max = 50)
  @Comment("브랜드명")
  private String name;

  @Builder(access = AccessLevel.PRIVATE)
  public Brand(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Brand of(String name) {
    return Brand.builder()
        .name(name)
        .build();
  }

  /**
   * 브랜드명을 수정한다.
   *
   * @param name 수정할 브랜드명
   */
  public void updateName(String name) {
    this.name = name;
  }

  /**
   * 브랜드를 삭제한다.
   */
  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Brand brand)) {
      return false;
    }
    return Objects.equals(id, brand.id) && Objects.equals(name, brand.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
