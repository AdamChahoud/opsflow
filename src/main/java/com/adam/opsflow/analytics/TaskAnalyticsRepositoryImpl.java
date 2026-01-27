package com.adam.opsflow.analytics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TaskAnalyticsRepositoryImpl implements TaskAnalyticsRepository{
    @PersistenceContext
    private EntityManager em;
    @Override
    public long countAllTasks(){
        return ((Number) em.createNativeQuery(
                "SELECT COUNT(*) FROM tasks"
        ).getSingleResult()).longValue();
    }

    @Override
    public Map<String, Long> countTasksByStatus() {
        List<Object[]> results = em.createQuery("""
                SELECT t.status, COUNT(t)
                FROM Task t
                GROUP BY t.status
        """, Object[].class).getResultList();

        return results.stream()
                .collect(Collectors.toMap(
                        row -> row[0].toString(),
                        row -> (Long) row[1]
                ));

    }
}
