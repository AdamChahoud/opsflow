package com.adam.opsflow.auth;

public record RegisterRequest (
        String email,
        String password,
        String role
) {}