package com.adam.opsflow.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStatusNotAndDueAtBefore(TaskStatus status, Instant now);

}
