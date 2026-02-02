package com.adam.opsflow.analytics;

import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class AnalyticsService {
    private final TaskAnalyticsRepository analyticsRepository;
    public AnalyticsService(TaskAnalyticsRepository analyticsRepository){
        this.analyticsRepository = analyticsRepository;
    }
    public Map<String, Long> getTaskCountByStatus(){
        return analyticsRepository.countTasksByStatus();
    }
    public long getAllTasks(){
        return analyticsRepository.countAllTasks();
    }
    public Double getAverageCompletionTime(){
        return analyticsRepository.averageCompletionTime();
    }
}
