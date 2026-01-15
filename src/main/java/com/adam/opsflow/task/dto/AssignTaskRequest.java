package com.adam.opsflow.task.dto;

import java.util.UUID;

public record AssignTaskRequest (
        UUID assigneeId
){
}
