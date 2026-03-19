package com.adam.opsflow.audit.mongo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoAuditLogRepository
        extends MongoRepository<MongoAuditLog, String> {
    @Aggregation(pipeline = {
            "{ $match: { action: { $ne: null } } }",
            "{ $group: { _id: '$action', count: { $sum: 1 } } }"
    })
    List<ActionCount> countByAction();

    @Aggregation(pipeline = {
            "{ $match: { createdAt: { $ne: null } } }",
            "{ $group: { " +
            "_id: { $dateToString: { format: '%Y-%m-%d', date: '$createdAt' } }, " +
            "count: { $sum: 1 } } }",
            "{ $sort: { _id: 1 } }"

    })
    List<ActionCount> countActionPerDay();
}
