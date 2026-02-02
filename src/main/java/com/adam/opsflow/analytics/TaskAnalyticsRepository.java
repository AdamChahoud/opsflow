package com.adam.opsflow.analytics;

import java.util.Map;

public interface TaskAnalyticsRepository {
    long countAllTasks();
    Map<String, Long> countTasksByStatus();
    Double averageCompletionTime();
}
