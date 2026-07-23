package dev.company.server.gateway.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class DemoUserService {

	private final JwtProperties jwtProperties;

	public DemoUserService(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	public DemoUser authenticate(LoginRequest request) {
		return users().stream()
				.filter(user -> user.username().equalsIgnoreCase(request.username()))
				.filter(user -> user.password().equals(request.password()))
				.findFirst()
				.orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
	}

	public Optional<DemoUser> findByUsername(String username) {
		return users().stream()
				.filter(user -> user.username().equalsIgnoreCase(username))
				.findFirst();
	}

	private List<DemoUser> users() {
		return List.of(
				new DemoUser(jwtProperties.getDemoUserUsername(), jwtProperties.getDemoUserPassword(), List.of("USER")),
				new DemoUser(jwtProperties.getDemoAdminUsername(), jwtProperties.getDemoAdminPassword(),
						List.of("ADMIN", "EMPLOYEE")));
	}
}
