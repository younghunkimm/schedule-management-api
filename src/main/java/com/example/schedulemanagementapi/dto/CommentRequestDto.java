package com.example.schedulemanagementapi.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long scheduleId;
    private String contents;
    private String name;
    private String password;
}
