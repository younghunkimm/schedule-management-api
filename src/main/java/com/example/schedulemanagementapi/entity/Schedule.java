package com.example.schedulemanagementapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String author; // 작성자명

    private String password; // 비밀번호

}
