package dev.company.server.gateway.security;

import java.util.List;

public record DemoUser(String username, String password, List<String> roles) {
}
