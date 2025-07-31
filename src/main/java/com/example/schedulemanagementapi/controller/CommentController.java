package com.example.schedulemanagementapi.controller;

import com.example.schedulemanagementapi.dto.CommentRequestDto;
import com.example.schedulemanagementapi.dto.CommentResponseDto;
import com.example.schedulemanagementapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
        @PathVariable Long scheduleId,
        @RequestBody CommentRequestDto requestDto
    ) {

        return new ResponseEntity<>(commentService.saveComment(scheduleId, requestDto), HttpStatus.CREATED);
    }
}
