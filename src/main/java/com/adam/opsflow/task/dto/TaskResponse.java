package com.adam.opsflow.task.dto;

import com.adam.opsflow.task.Task;
import com.adam.opsflow.task.TaskStatus;

import java.time.Instant;
import java.util.UUID;

public record TaskResponse (
        UUID id,
        String title,
        String description,
        TaskStatus status,
        UUID createdBy,
        UUID assignedTo,
        Instant createdAt,
        Instant updatedAt
){
    public static TaskResponse from(Task task){
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedBy(),
                task.getAssignedTo(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
