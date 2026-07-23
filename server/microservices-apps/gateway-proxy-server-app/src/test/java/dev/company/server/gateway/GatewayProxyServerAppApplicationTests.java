package dev.company.server.gateway;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"eureka.client.enabled=false",
		"spring.cloud.discovery.enabled=false"
})
class GatewayProxyServerAppApplicationTests {

	@LocalServerPort
	private int port;

	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		webTestClient = WebTestClient.bindToServer()
				.baseUrl("http://localhost:" + port)
				.build();
	}

	@Test
	void loginReturnsBearerToken() {
		webTestClient.post()
				.uri("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("""
						{"username":"user","password":"user123"}
						""")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.tokenType").isEqualTo("Bearer")
				.jsonPath("$.accessToken").isNotEmpty()
				.jsonPath("$.username").isEqualTo("user");
	}

	@Test
	void loginRejectsInvalidCredentials() {
		webTestClient.post()
				.uri("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("""
						{"username":"user","password":"wrong"}
						""")
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	void protectedRoutesRejectMissingToken() {
		webTestClient.get()
				.uri("/customerapi/profile")
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	void meReturnsAuthenticatedPrincipal() {
		Map<?, ?> response = webTestClient.post()
				.uri("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("""
						{"username":"admin","password":"admin123"}
						""")
				.exchange()
				.expectStatus().isOk()
				.expectBody(Map.class)
				.returnResult()
				.getResponseBody();

		webTestClient.get()
				.uri("/auth/me")
				.headers(headers -> headers.setBearerAuth((String) response.get("accessToken")))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.username").isEqualTo("admin")
				.jsonPath("$.roles[0]").isEqualTo("ADMIN");
	}

}
