package com.adam.opsflow.analytics;

import com.adam.opsflow.analytics.mongo.AuditAnalyticsService;
import com.adam.opsflow.task.TaskStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/stats")
@PreAuthorize("hasRole('ADMIN')")
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    private final AuditAnalyticsService auditAnalyticsService;
    public AnalyticsController (AnalyticsService analyticsService,
                                AuditAnalyticsService auditAnalyticsService){
        this.analyticsService = analyticsService;
        this.auditAnalyticsService = auditAnalyticsService;
    }

    @GetMapping("/tasks-by-status")
    public Map<TaskStatus, Long> tasksByStatus() {
        return analyticsService.getTaskCountByStatus();
    }
    @GetMapping("/all-tasks")
    public long taskNumber(){
        return analyticsService.getAllTasks();
    }
    @GetMapping("/avg-completion-time")
    public Double averageCompletionTime(){
        return analyticsService.getAverageCompletionTime();
    }
    @GetMapping("/audit-actions")
    public Map<String, Long> auditActions(){
        return auditAnalyticsService.countAuditActions();
    }
}
