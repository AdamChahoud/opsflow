package com.adam.opsflow.audit.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAuditLogRepository
        extends MongoRepository<MongoAuditLog, String> {

}
