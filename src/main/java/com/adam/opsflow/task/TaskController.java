package com.adam.opsflow.task;

import com.adam.opsflow.task.dto.AssignTaskRequest;
import com.adam.opsflow.task.dto.CreateTaskRequest;
import com.adam.opsflow.task.dto.TaskResponse;
import com.adam.opsflow.task.dto.UpdateStatusRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse createTask(
            @RequestBody CreateTaskRequest request,
            Authentication auth
            ){
        UUID userId = (UUID) auth.getPrincipal();
        Task task = taskService.createTask(
                request.title(),
                request.description(),
                userId
        );
        return TaskResponse.from(task);
    }

    @PutMapping("/{id}/assign")
    public TaskResponse assignTask(
            @PathVariable UUID id,
            @RequestBody AssignTaskRequest request,
            Authentication auth
            ){
        String role = auth.getAuthorities().iterator()
                .next().getAuthority().replace("ROLE_", "");

        Task task = taskService.assignTask(
                id,
                request.assigneeId(),
                role
        );
        return TaskResponse.from(task);
    }

    @PutMapping("/{id}/status")
    public TaskResponse updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateStatusRequest request,
            Authentication auth
            ){
        UUID userId = (UUID) auth.getPrincipal();
        String role = auth.getAuthorities().iterator()
                .next().getAuthority().replace("ROLE_", "");

        Task task = taskService.updateStatus(
                id,
                request.status(),
                userId,
                role
        );
        return TaskResponse.from(task);
    }
}
