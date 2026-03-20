package com.adam.opsflow.notifications;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean read;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Notification(){}

    public Notification(UUID userId, String message){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.message = message;
        this.read = false;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void markAsRead(){
        this.read = true;
    }

}