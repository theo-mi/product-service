package com.mms.product.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Positive
  @Comment("카테고리 ID")
  private Long id;

  @Size(min = 1, max = 50)
  @Comment("카테고리명")
  private String name;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Category category)) {
      return false;
    }
    return Objects.equals(id, category.id) && Objects.equals(name, category.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
