package com.adam.opsflow.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditLogService {

    private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);
    private final AuditLogRepository repository;
    public AuditLogService(AuditLogRepository repository){
        this.repository = repository;
    }

    public void logEvent(
            String entityType,
            UUID entityId,
            String action,
            UUID performedBy,
            String metadata
    ){
        try{
            AuditLog entry = new AuditLog(
                    entityType,
                    entityId,
                    action,
                    performedBy,
                    metadata
            );
            repository.save(entry);
        } catch (Exception e){
            log.warn("Failed to write audit log: {}", e.getMessage());
        }
    }
}
