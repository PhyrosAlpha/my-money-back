package com.phyros.mymoney.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.phyros.mymoney.entity.User;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	private String issuer = "API MY MONEY";
	
	public String generateToken(User user) {
		try {	
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
							.withIssuer(issuer)
							.withSubject(user.getUsername())
							.withExpiresAt(this.generateExpiresDate())
							.sign(algorithm);		
			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error to generate token JWT", exception);
		}
	}
	
	public String getSubject(String tokenJWT) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer(issuer)
					.build()
					.verify(tokenJWT)
					.getSubject();
			
		} catch (JWTVerificationException exception) {
			throw new RuntimeException("Token JWT is invalid");
		}
	}
	
	private Instant generateExpiresDate() {
		return LocalDateTime
				.now()
				.plusHours(48)
				.toInstant(ZoneOffset.of("-03:00"));
	}
	
	public void readToken(String token) {
		JWT.decode(token);

	}
	
	
}
