package com.adam.opsflow.analytics;

import com.adam.opsflow.task.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final TaskRepository taskRepository;
    public AnalyticsService (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public Map<String, Long> getTaskCountByStatus() {
        return taskRepository.countTaskByStatus().stream()
                .collect(Collectors.toMap(
                        row -> row[0].toString(),
                        row -> (Long) row[1]

                ));
    }
}
