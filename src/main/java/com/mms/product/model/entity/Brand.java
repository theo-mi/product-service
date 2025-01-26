package com.mms.product.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String name;

  @Builder(access = AccessLevel.PRIVATE)
  public Brand(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Brand of(Long id, String name) {
    return Brand.builder()
        .id(id)
        .name(name)
        .build();
  }

  public static Brand of(String name) {
    return Brand.builder()
        .name(name)
        .build();
  }

  /**
   * 브랜드 이름을 수정한다.
   *
   * @param name 수정할 브랜드 이름
   */
  public void updateName(String name) {
    this.name = name;
  }
}
