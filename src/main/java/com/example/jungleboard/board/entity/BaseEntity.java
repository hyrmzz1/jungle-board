package com.example.jungleboard.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 시간 관련 entity
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)  // 수정시 이 변수는 관여 X
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false) // 입력(생성)시 이 변수는 관여 X
    private LocalDateTime UpdatedTime;
}
