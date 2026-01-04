package com.adam.opsflow.auth;

public record LoginRequest (
        String email,
        String password
) {}