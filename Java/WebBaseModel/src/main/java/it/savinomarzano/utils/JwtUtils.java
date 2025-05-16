package it.savinomarzano.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.token.expiration}")
	private long jwtExpirationInMillis;

	public String generateToken(String email) {

		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, email);

	}

	private String createToken(Map<String, Object> claims, String email) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();

	}

	private Key getSignKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);

	}

	public String extractEmail(String token) {

		return extractClaim(token, Claims::getSubject);

	}

	public Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);

	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);

	}

	private Claims extractAllClaims(String token) {

		try {
			return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.error("Eccezione nella decodifica del token");
			return null;
		}
	}

	private Boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());

	}

	public Boolean validateToken(String token, UserDetails userDetails) {

		final String email = extractEmail(token);
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));

	}

}
