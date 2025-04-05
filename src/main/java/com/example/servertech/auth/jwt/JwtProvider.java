package com.example.servertech.auth.jwt;

import com.example.servertech.auth.exception.InvalidJwtException;
import com.example.servertech.auth.exception.UnauthorizedUserException;
import com.example.servertech.domain.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
@RequiredArgsConstructor
public class JwtProvider {
	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";
	@Value("${jwt.issuer}")
	private String JWT_ISSUER;
	@Value("${jwt.key}")
	private String SECRET_KEY;


	public String generateAccessToken(Long id, UserRole role) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + Duration.ofHours(1).toMillis()), id, role);
	}

	private String makeToken(Date expiry, Long id, UserRole role) {
		Date now = new Date();

		return Jwts.builder()
			.setHeaderParam(TYPE, JWT_TYPE)
			.setIssuer(JWT_ISSUER)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.setSubject(id.toString())
			.claim("role", role.name())
			.signWith(HS256, SECRET_KEY)
			.compact();
	}

	public boolean validateToken(String token) {
		if (token == null) {
			return false;
		}

		try {
			Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new InvalidJwtException();
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		Set<SimpleGrantedAuthority> authorities = getRoles(claims);

		return new UsernamePasswordAuthenticationToken(
			new org.springframework.security.core.userdetails.User(
				claims.getSubject(),
				"",
				authorities
			), token, authorities
		);
	}

	public Set<SimpleGrantedAuthority> getRoles(Claims claims) {
		String role = claims.get("role", String.class);

		return switch (role) {
			case "ADMIN" -> Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
			case "NORMAL" -> Collections.singleton(new SimpleGrantedAuthority("ROLE_NORMAL"));
			default -> throw new UnauthorizedUserException();
		};
	}

	public String extractAccessToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			return authorizationHeader.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(SECRET_KEY)
			.parseClaimsJws(token)
			.getBody();
	}
}
