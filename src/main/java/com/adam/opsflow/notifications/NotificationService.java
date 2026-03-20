package com.adam.opsflow.notifications;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    @Async
    public void createNotification(UUID userId, String message){
        Notification notification = new Notification(userId, message);
        repository.save(notification);
    }

    public List<Notification> getUserNotifications(UUID userId){
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(UUID notificationId){
        Notification notification = repository.findById(notificationId)
                .orElseThrow(()-> new RuntimeException("Notification not found"));
        notification.markAsRead();
        repository.save(notification);
    }
}
