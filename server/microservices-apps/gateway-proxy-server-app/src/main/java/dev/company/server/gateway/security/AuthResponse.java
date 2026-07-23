package dev.company.server.gateway.security;

import java.time.Instant;
import java.util.List;

public record AuthResponse(String accessToken, String tokenType, Instant expiresAt, String username, List<String> roles) {
}
