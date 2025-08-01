package com.example.schedulemanagementapi.entity;

import com.example.schedulemanagementapi.global.security.PasswordHolder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity implements PasswordHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    - @ManyToOne: N:1 관계
        - fetch: 연관된 Entity를 언제 로딩할지 결정
            - `ManyToOne`은 기본이 `EAGER`이기 때문에 명시적으로 작성해주는게 좋다!
        - FetchType.LAZY: 지연 로딩
            - `Comment`를 조회하면 `Schedule`은 조회하지 않음
            - `getSchedule()`을 호출하는 순간 쿼리 실행
            - 불필요한 JOIN 방지
        - FetchType.EAGER: 즉시 로딩 (즉시 JOIN)
            - `Comment`를 조회할 때 무조건 연관된 `Schedule`도 함께 조회 (JOIN 사용)

    - @JoinColumn
        - 외래키의 Column명을 지정한다.
        - 이 외래키는 `Schedule` 테이블의 기본키를 참조
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule; // 일정

    private String contents; // 댓글 내용

    private String name; // 작성자명

    private String password; // 비밀번호

    public Comment(Schedule schedule, String contents, String name, String password) {
        this.schedule = schedule;
        this.contents = contents;
        this.name = name;
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
