package com.adam.opsflow.task;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Task getTask(UUID taskId, UUID userId, String role){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        boolean isAdmin = role.equals("ADMIN");
        boolean isCreator = userId.equals(task.getCreatedBy());
        boolean isAssignee = userId.equals(task.getAssignedTo());

        if (!isAdmin && !isAssignee && !isCreator){
            throw new AccessDeniedException("Not allowed to view this task");
        }
        return task;
    }

    public List<Task> getAllTasks(UUID userId, String role){
        if (role.equals("ADMIN")){
            return taskRepository.findAll();
        }

        return taskRepository.findAll().stream().filter(
                task -> userId.equals(task.getCreatedBy())
                || userId.equals(task.getAssignedTo())
        ).toList();
    }

    public Comment addComment(
            UUID taskId,
            UUID authorId,
            String role,
            String content
    ){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        boolean isAdmin = role.equals("ADMIN");
        boolean isCreator = authorId.equals(task.getCreatedBy());
        boolean isAssignee = authorId.equals(task.getAssignedTo());

        if(!isAdmin && !isCreator && !isAssignee){
            throw new AccessDeniedException("Not allowed to comment on this task");
        }

        Comment comment = new Comment(taskId, authorId, content);
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(UUID taskId){
        return commentRepository.findByTaskId(taskId);
    }

}
