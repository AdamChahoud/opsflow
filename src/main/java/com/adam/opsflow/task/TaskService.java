package com.adam.opsflow.task;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public TaskService(
            TaskRepository taskRepository,
            CommentRepository commentRepository
    ){
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    public Task createTask(String title, String description, UUID creatorId){
        Task task = new Task(title, description, creatorId);
        return taskRepository.save(task);
    }

    public Task assignTask(UUID taskId, UUID assigneeId, String role){
        if (!role.equals("MANAGER") && !role.equals("ADMIN")){
            throw new AccessDeniedException("Only managers or admins can assign tasks");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.assignTo(assigneeId);
        return taskRepository.save(task);
    }

    public Task updateStatus(UUID taskId, TaskStatus newStatus, UUID userId, String role){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        boolean isAdmin = role.equals("ADMIN");
        boolean isAssignee = userId.equals(task.getAssignedTo());

        if (!isAdmin && !isAssignee){
            throw new AccessDeniedException("Not allowed to update task status");
        }

        task.updateStatus(newStatus);
        return taskRepository.save(task);
    }

}
