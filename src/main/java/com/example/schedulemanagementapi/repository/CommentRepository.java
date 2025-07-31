package com.example.schedulemanagementapi.repository;

import com.example.schedulemanagementapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
