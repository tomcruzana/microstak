package dev.company.server.gateway.security;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
			Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter) {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.authorizeExchange(exchanges -> exchanges
						.pathMatchers(HttpMethod.OPTIONS).permitAll()
						.pathMatchers("/auth/login", "/actuator/health", "/actuator/info").permitAll()
						.pathMatchers("/actuator/**").hasRole("ADMIN")
						.pathMatchers("/contactusapi/contact_us/all", "/contactusapi/contact_us/q", "/employeeapi/**")
						.hasAnyRole("ADMIN", "EMPLOYEE")
						.pathMatchers("/customerapi/**").hasAnyRole("USER", "ADMIN")
						.pathMatchers("/aboutusapi/**", "/companyapi/**", "/couponapi/validate", "/faqapi/**",
								"/guestemailapi/subscribe", "/guestemailapi/total", "/newsandupdatesapi/**",
								"/privacypolicyapi/**", "/productitemapi/**", "/shippingandreturnsapi/**",
								"/termsofuseapi/**")
						.permitAll()
						.anyExchange().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
				.build();
	}

	@Bean
	Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}

	@Bean
	JwtEncoder jwtEncoder(JwtProperties jwtProperties) {
		return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey(jwtProperties)));
	}

	@Bean
	ReactiveJwtDecoder reactiveJwtDecoder(JwtProperties jwtProperties) {
		return NimbusReactiveJwtDecoder.withSecretKey(jwtSecretKey(jwtProperties))
				.macAlgorithm(MacAlgorithm.HS256)
				.build();
	}

	@Bean
	JwtDecoder jwtDecoder(JwtProperties jwtProperties) {
		return NimbusJwtDecoder.withSecretKey(jwtSecretKey(jwtProperties))
				.macAlgorithm(MacAlgorithm.HS256)
				.build();
	}

	@Bean
	CorsWebFilter corsWebFilter(JwtProperties jwtProperties) {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(jwtProperties.getCorsAllowedOrigins());
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
		corsConfiguration.setExposedHeaders(List.of("Authorization"));
		corsConfiguration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsWebFilter(source);
	}

	private SecretKey jwtSecretKey(JwtProperties jwtProperties) {
		return new SecretKeySpec(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
	}
}
