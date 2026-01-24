package com.adam.opsflow.task;

import com.adam.opsflow.task.dto.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        UUID performedBy = (UUID) auth.getPrincipal();
        String role = auth.getAuthorities().iterator()
                .next().getAuthority().replace("ROLE_", "");

        Task task = taskService.assignTask(
                id,
                request.assigneeId(),
                performedBy,
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

    @GetMapping("/{id}")
    public TaskResponse getTask(
            @PathVariable UUID id,
            Authentication auth
    ){
        UUID userId = (UUID) auth.getPrincipal();
        String role = auth.getAuthorities().iterator()
                .next().getAuthority().replace("ROLE_", "");

        Task task = taskService.getTask(id, userId, role);
        return TaskResponse.from(task);
    }

    @GetMapping
    public List<TaskResponse> getTasks(Authentication auth){
        UUID userId = (UUID) auth.getPrincipal();
        String role = auth.getAuthorities().iterator()
                .next().getAuthority().replace("ROLE_", "");

        return taskService.getAllTasks(userId, role).stream()
                .map(TaskResponse::from).toList();
    }

    @PostMapping("/{id}/comments")
    public CommentResponse addComment(
            @PathVariable UUID id,
            @RequestBody CreateCommentRequest request,
            Authentication auth
    ){
        UUID userId = (UUID) auth.getPrincipal();
        String role = auth.getAuthorities().iterator()
                .next().getAuthority().replace("ROLE_", "");

        Comment comment = taskService.addComment(
                id,
                userId,
                role,
                request.content()
        );
        return CommentResponse.from(comment);
    }

    @GetMapping("/{id}/comments")
    public List<CommentResponse> getComments(@PathVariable UUID id){
        return taskService.getComments(id).stream()
                .map(CommentResponse::from).toList();
    }
}
