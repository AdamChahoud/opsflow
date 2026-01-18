package com.adam.opsflow.task.dto;

import com.adam.opsflow.task.Comment;

import java.time.Instant;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        UUID taskId,
        UUID authorId,
        String content,
        Instant createdAt
) {
    public static CommentResponse from(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getTaskId(),
                comment.getAuthorId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
