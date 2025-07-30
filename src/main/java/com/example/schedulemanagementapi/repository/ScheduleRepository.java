package com.example.schedulemanagementapi.repository;

import com.example.schedulemanagementapi.dto.ScheduleResponseDto;
import com.example.schedulemanagementapi.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h2>제네릭 타입에 DTO를 못넣는 이유</h2>
 * <ul>
 *     <li>{@link JpaRepository}는 엔티티 클래스만 사용할 수 있다.</li>
 *     <li>즉, {@code @Entity}가 붙은 클래스</li>
 *     <li>{@link ScheduleResponseDto}는 일반적으로 응답을 위한 DTO일 뿐, DB와 매핑된 엔티티가 아니기 때문에 JPA에서는 사용할 수 없다.</li>
 * </ul>
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * <ul>
     *     <li>JPA가 메서드 이름을 해석하여 자동으로 JPQL 쿼리를 생성</li>
     *     <li>{@code findByNameContaining} -> {@code LIKE %값%}</li>
     * </ul>
     */
    List<Schedule> findByNameContainingOrderByModifiedAtDesc(String name);

    List<Schedule> findAllByOrderByModifiedAtDesc();
}
