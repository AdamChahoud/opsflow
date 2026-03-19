package com.adam.opsflow.analytics.mongo;

import com.adam.opsflow.audit.mongo.ActionCount;
import com.adam.opsflow.audit.mongo.MongoAuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuditAnalyticsService {
    private final MongoAuditLogRepository repository;
    public AuditAnalyticsService(MongoAuditLogRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Map<String, Long> countAuditActions() {
        return repository.countByAction().stream().collect(
                Collectors.toMap(ActionCount::getAction,
                        ActionCount::getCount)
        );
    }
}
