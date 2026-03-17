package com.adam.opsflow.audit;
import com.adam.opsflow.audit.mongo.MongoAuditLog;
import com.adam.opsflow.audit.mongo.MongoAuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class AuditLogService {

    private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuditLogRepository repository;
    private final MongoAuditLogRepository mongoRepository;
    public AuditLogService(
            AuditLogRepository repository,
            MongoAuditLogRepository mongoRepository
            ){
        this.repository = repository;
        this.mongoRepository = mongoRepository;
    }

    public void logEvent(
            String entityType,
            UUID entityId,
            String action,
            UUID performedBy,
            Map<String, Object> metadata
    ){
        try{
            String json = objectMapper.writeValueAsString(metadata);
            AuditLog entry = new AuditLog(
                    entityType,
                    entityId,
                    action,
                    performedBy,
                    json
            );
            repository.save(entry);
            MongoAuditLog mongoEntry = new MongoAuditLog(
                    entityType,
                    entityId,
                    action,
                    performedBy,
                    Instant.now(),
                    metadata
            );
            mongoRepository.save(mongoEntry);

        } catch (Exception e){
            log.warn("Failed to write audit log: {}", e.getMessage());
        }
    }
}
