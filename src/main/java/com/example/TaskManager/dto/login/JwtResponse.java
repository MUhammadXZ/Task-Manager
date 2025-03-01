package com.example.TaskManager.dto.login;

import java.util.UUID;

public record JwtResponse(
        String accessToken,
        UUID userId,
        String email,
        java.util.Date expiresAt
) {}
