package com.adam.opsflow.task;

import com.adam.opsflow.audit.AuditLogRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class TaskServiceAuditTest {

    @Autowired
    TaskService taskService;
    @Autowired
    AuditLogRepository auditLogRepository;
    @Test
    void createsAuditLog_whenTaskIsCreated(){
        UUID userId = UUID.randomUUID();
        taskService.createTask("Test task", "desc", userId);
        var logs = auditLogRepository.findAll();

        assertThat(logs).hasSize(1);
        assertThat(logs.get(0).getAction()).isEqualTo("TASK_CREATED");
        assertThat(logs.get(0).getPerformedBy()).isEqualTo(userId);
    }

    @Test
    void createsAuditLog_whenTaskIsAssigned(){
        UUID adminId = UUID.randomUUID();
        UUID assigneeId = UUID.randomUUID();

        Task task = taskService.createTask("Task", "desc", adminId);
        taskService.assignTask(task.getId(), assigneeId, adminId, "ADMIN");
        var logs = auditLogRepository.findAll();
        assertThat(logs).anyMatch(log -> log.getAction().equals("TASK_ASSIGNED"));
    }
}
