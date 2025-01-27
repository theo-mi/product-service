package com.mms.product.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

  @ColumnDefault(value = "CURRENT_TIMESTAMP")
  @CreatedDate
  @Comment("등록 시각")
  protected LocalDateTime createdAt;

  @ColumnDefault(value = "CURRENT_TIMESTAMP")
  @LastModifiedDate
  @Comment("수정 시각")
  protected LocalDateTime updatedAt;

  @Comment("삭제 시각")
  protected LocalDateTime deletedAt;
}
