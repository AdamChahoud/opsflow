package com.adam.opsflow.analytics;

import com.adam.opsflow.task.Task;
import com.adam.opsflow.task.TaskRepository;
import com.adam.opsflow.task.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void returnsTaskStats_forAdmin() throws Exception {
        UUID creatorId = UUID.randomUUID();
        Task openTask = new Task("Open task", "Still in progress", creatorId, Instant.now().plusSeconds(3600));
        Task doneTask = new Task("Done task", "Completed", creatorId, Instant.now().plusSeconds(3600));
        doneTask.updateStatus(TaskStatus.DONE);

        taskRepository.save(openTask);
        taskRepository.save(doneTask);

        mockMvc.perform(get("/admin/stats/tasks-by-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.OPEN").value(1))
                .andExpect(jsonPath("$.DONE").value(1));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deniesTaskStats_forNonAdmin() throws Exception {
        mockMvc.perform(get("/admin/stats/tasks-by-status"))
                .andExpect(status().isForbidden());
    }

}
