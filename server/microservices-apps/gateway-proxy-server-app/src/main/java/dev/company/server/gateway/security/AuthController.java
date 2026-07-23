package dev.company.server.gateway.security;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final DemoUserService demoUserService;
	private final JwtTokenService jwtTokenService;

	public AuthController(DemoUserService demoUserService, JwtTokenService jwtTokenService) {
		this.demoUserService = demoUserService;
		this.jwtTokenService = jwtTokenService;
	}

	@GetMapping("/login")
	public ResponseEntity<Map<String, String>> loginInfo() {
		return ResponseEntity.ok(Map.of(
				"message", "Submit a POST request to /auth/login with username and password JSON.",
				"frontend", "http://localhost:8000/user_sign_in.html",
				"exampleBody", "{\"username\":\"user\",\"password\":\"user123\"}"));
	}

	@PostMapping("/login")
	public Mono<ResponseEntity<AuthResponse>> login(@RequestBody Mono<LoginRequest> request) {
		return request
				.map(demoUserService::authenticate)
				.map(jwtTokenService::createToken)
				.map(ResponseEntity::ok)
				.onErrorMap(BadCredentialsException.class,
						ex -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage()));
	}

	@GetMapping("/me")
	public ResponseEntity<AuthResponse> me(JwtAuthenticationToken authentication) {
		DemoUser user = demoUserService.findByUsername(authentication.getName())
				.orElse(new DemoUser(authentication.getName(), "", authentication.getToken().getClaimAsStringList("roles")));
		return ResponseEntity.ok(jwtTokenService.createToken(user));
	}
}
