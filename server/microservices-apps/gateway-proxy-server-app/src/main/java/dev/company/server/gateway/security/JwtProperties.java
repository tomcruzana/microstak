package dev.company.server.gateway.security;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {

	private String issuer = "microstak-gateway";
	private String secret = "microstak-demo-secret-key-change-me-please-1234567890";
	private Duration tokenTtl = Duration.ofHours(2);
	private List<String> corsAllowedOrigins = List.of("http://localhost:8000", "http://127.0.0.1:8000");
	private String demoUserUsername = "user";
	private String demoUserPassword = "user123";
	private String demoAdminUsername = "admin";
	private String demoAdminPassword = "admin123";

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Duration getTokenTtl() {
		return tokenTtl;
	}

	public void setTokenTtl(Duration tokenTtl) {
		this.tokenTtl = tokenTtl;
	}

	public List<String> getCorsAllowedOrigins() {
		return corsAllowedOrigins;
	}

	public void setCorsAllowedOrigins(List<String> corsAllowedOrigins) {
		this.corsAllowedOrigins = corsAllowedOrigins;
	}

	public String getDemoUserUsername() {
		return demoUserUsername;
	}

	public void setDemoUserUsername(String demoUserUsername) {
		this.demoUserUsername = demoUserUsername;
	}

	public String getDemoUserPassword() {
		return demoUserPassword;
	}

	public void setDemoUserPassword(String demoUserPassword) {
		this.demoUserPassword = demoUserPassword;
	}

	public String getDemoAdminUsername() {
		return demoAdminUsername;
	}

	public void setDemoAdminUsername(String demoAdminUsername) {
		this.demoAdminUsername = demoAdminUsername;
	}

	public String getDemoAdminPassword() {
		return demoAdminPassword;
	}

	public void setDemoAdminPassword(String demoAdminPassword) {
		this.demoAdminPassword = demoAdminPassword;
	}
}
