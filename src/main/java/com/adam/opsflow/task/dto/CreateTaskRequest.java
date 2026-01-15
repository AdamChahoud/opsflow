package com.adam.opsflow.task.dto;

public record CreateTaskRequest (
        String title,
        String description
){
}
