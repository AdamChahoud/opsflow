package com.adam.opsflow.notifications;

import com.adam.opsflow.task.Task;
import com.adam.opsflow.task.TaskRepository;
import com.adam.opsflow.task.TaskStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class OverdueTaskSchedular {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    public OverdueTaskSchedular(TaskRepository taskRepository, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 60000)
    public void checkOverdueTask(){
        List<Task> overdueTasks = taskRepository.findByStatusNotAndDueAtBefore(TaskStatus.DONE, Instant.now());

        for (Task task : overdueTasks){
            if(task.getAssignedTo() != null){
                notificationService.createNotification(
                        task.getAssignedTo(),
                        "Task overdue: " + task.getTitle()
                );
            }
        }
    }
}
