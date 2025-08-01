package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.CommentRequestDto;
import com.example.schedulemanagementapi.dto.CommentResponseDto;
import com.example.schedulemanagementapi.entity.Comment;
import com.example.schedulemanagementapi.entity.Schedule;
import com.example.schedulemanagementapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleValidator scheduleValidator;

    @Transactional
    @Override
    public CommentResponseDto saveComment(Long scheduleId, CommentRequestDto requestDto) {

        // 필수값 체크
        if (!StringUtils.hasText(requestDto.getContents())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is required");
        }

        if (!StringUtils.hasText(requestDto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }

        if (!StringUtils.hasText(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        // 길이 제한
        if (requestDto.getContents().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The length of the content is exceeded. Please write within 100 characters.");
        }

        // scheduleId로 일정을 조회
        Schedule schedule = scheduleValidator.findScheduleByIdOrElseThrow(scheduleId);

        // 댓글 개수 체크 (최대 10개)
        int commentCount = schedule.getComments().size();
        if (commentCount >= 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of comments is up to 10.");
        }

        // 댓글 생성
        Comment comment = new Comment(
                schedule,
                requestDto.getContents(),
                requestDto.getName(),
                requestDto.getPassword()
        );

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

}
