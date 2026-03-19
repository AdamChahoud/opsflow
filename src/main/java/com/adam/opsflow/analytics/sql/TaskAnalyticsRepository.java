package com.adam.opsflow.analytics.sql;

import com.adam.opsflow.task.TaskStatus;

import java.util.Map;

public interface TaskAnalyticsRepository {
    long countAllTasks();
    Map<TaskStatus, Long> countTasksByStatus();
    Double averageCompletionTime();
}
