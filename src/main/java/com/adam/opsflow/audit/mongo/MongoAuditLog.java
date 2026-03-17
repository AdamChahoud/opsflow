package com.adam.opsflow.audit.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Document("audit_events")
public class MongoAuditLog {
    @Id
    private String id;
    private String entityType;
    private UUID entityId;
    private String action;
    private UUID performedBy;
    private Instant createdAt;
    private Map<String, Object> metadata;

    protected MongoAuditLog() {}

    public MongoAuditLog(
            String entityType,
            UUID entityId,
            String action,
            UUID performedBy,
            Instant createdAt,
            Map<String, Object> metadata
    ){
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.performedBy = performedBy;
        this.createdAt = createdAt;
        this.metadata = metadata;
    }

}
