package com.example.schedulemanagementapi.dto;

import com.example.schedulemanagementapi.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto {

    private final Long id;
    private final Long scheduleId;
    private final String contents;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {

        this.id = comment.getId();
        this.scheduleId = comment.getSchedule().getId();
        this.contents = comment.getContents();
        this.name = comment.getName();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
