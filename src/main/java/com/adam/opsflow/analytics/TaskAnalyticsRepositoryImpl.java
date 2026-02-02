package com.adam.opsflow.analytics;

import com.adam.opsflow.task.TaskStatus;
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
    public Map<TaskStatus, Long> countTasksByStatus() {
        List<Object[]> results = em.createQuery("""
                SELECT t.status, COUNT(t)
                FROM Task t
                GROUP BY t.status
        """, Object[].class).getResultList();

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (TaskStatus) row[0],
                        row -> (Long) row[1]
                ));

    }
    @Override
    public Double averageCompletionTime(){
        return (Double) em.createNativeQuery("""
                SELECT AVG(EXTRACT(EPOCH FROM (updated_at - created_at)))
                FROM tasks
                Where status = 'DONE'
                """).getSingleResult();
    }
}
