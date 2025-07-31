package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.CommentRequestDto;
import com.example.schedulemanagementapi.dto.CommentResponseDto;

public interface CommentService {

    CommentResponseDto saveComment(Long scheduleId, CommentRequestDto requestDto);

}
