package dev.company.server.gateway.security;

import java.time.Instant;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

	private final JwtEncoder jwtEncoder;
	private final JwtProperties jwtProperties;

	public JwtTokenService(JwtEncoder jwtEncoder, JwtProperties jwtProperties) {
		this.jwtEncoder = jwtEncoder;
		this.jwtProperties = jwtProperties;
	}

	public AuthResponse createToken(DemoUser user) {
		Instant issuedAt = Instant.now();
		Instant expiresAt = issuedAt.plus(jwtProperties.getTokenTtl());
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer(jwtProperties.getIssuer())
				.issuedAt(issuedAt)
				.expiresAt(expiresAt)
				.subject(user.username())
				.claim("roles", user.roles())
				.build();

		String token = jwtEncoder.encode(JwtEncoderParameters.from(
				JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
		return new AuthResponse(token, "Bearer", expiresAt, user.username(), user.roles());
	}
}
