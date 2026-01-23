package com.adam.opsflow.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    private UUID id;
    @Column(name = "entity_type", nullable = false)
    private String entityType;
    @Column(name = "entity_id")
    private UUID entityId;
    @Column(nullable = false)
    private String action;
    @Column(name = "performed_by")
    private UUID performedBy;
    @Column(columnDefinition = "jsonb")
    private String metadata;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected AuditLog() {}

    public AuditLog(
            String entityType,
            UUID entityId,
            String action,
            UUID performedBy,
            String metadata
    ){
        this.id = UUID.randomUUID();
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.performedBy = performedBy;
        this.metadata = metadata;
        this.createdAt = Instant.now();
    }

}
