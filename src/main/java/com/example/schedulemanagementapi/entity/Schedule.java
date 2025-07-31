package com.example.schedulemanagementapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseEntity {

    @Id // PRIMARY KEY 로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private Long id;

    private String title; // 일정 제목

    private String contents; // 일정 내용

    private String name; // 작성자명

    private String password; // 비밀번호

    /*
    - @OneToMany: 1:N 관계
    - mappedBy: Comment의 `schedule` 멤버에 매핑
    - cascade: 영속성 전이
        - CascadeType
            - CascadeType.PERSIST: 부모 `save()` 시 자식도 자동 저장
            - CascadeType.MERGE: 부모 'merge()` 시 자식도 병합
            - CascadeType.REMOVE: 부모 'remove()` 시 자식도 함께 삭제
            - CascadeType.DETACH: 부모가 detach될 때 자식도 detach
            - CascadeType.REFRESH: 부모 새로고침 시 자식도 새로고침
            - CascadeType.ALL: 위 모든 옵션을 포함
        - 실제 DB에서 On DELETE CASCADE 설정❌, JPA 내부적으로 처리하기 때문
     - OrphanRemoval: 고아 객체 자동 삭제
        - 컬렉션(List 등)에서 엔티티를 제거하면, DB에서도 삭제되게 한다.
        - JPA가 "고아 객체"를 감지하여 삭제
        - 삭제는 `delete` 쿼리로 실행된다.
     */
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Schedule(String title, String contents, String name, String password) {
        this.title = title;
        this.contents = contents;
        this.name = name;
        this.password = password;
    }

    // 제목 수정을 위한 Setter
    public void updateTitle(String title) {
        this.title = title;
    }

    // 이름 수정을 위한 Setter
    public void updateName(String name) {
        this.name = name;
    }
}
