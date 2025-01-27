package com.mms.product.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("브랜드 ID")
  private Long id;

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
}
