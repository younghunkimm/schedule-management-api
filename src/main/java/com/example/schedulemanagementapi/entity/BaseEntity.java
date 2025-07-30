package com.example.schedulemanagementapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * <h2>{@code @MappedSuperClass}</h2>
 * <ul>
 *     <li>
 *         해당 어노테이션이 있어야 JPA가 다음과 같이 동작한다.
 *         <ul>
 *             <li>해당 클래스를 엔티티로 취급하지 않는다.</li>
 *             <li>해당 클래스를 상속받는 엔티티에 필드만 전달한다.</li>
 *         </ul>
 *     </li>
 * </ul>
 * <hr>
 * <h2>{@code @EntityListeners(AuditingEntityListener.class)}</h2>
 * <ul>
 *     <li>JPA Entity Class의 생명주기(생성, 수정)를 감지</li>
 *     <li>
 *         JPA 생명주기 콜백 시점에 값이 자동으로 주입된다.
 *         <ul>
 *             <li>{@code @PrePersist} -> insert 전에 {@code createdDate} 자동 설정</li>
 *             <li>{@code @PreUpdate} -> update 전에 {@code modifiedDate} 자동 설정</li>
 *         </ul>
 *     </li>
 * </ul>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

}
