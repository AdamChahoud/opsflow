package com.adam.opsflow.task.dto;

import com.adam.opsflow.task.TaskStatus;

public record UpdateStatusRequest (
        TaskStatus status
){
}
