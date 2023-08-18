package unq.edu.li.pdes.micultura.utils;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtils {

	@Value("${jwt.secret}")
	private String ACCESS_TOKEN_SECRET;

	@Value("${jwt.validaty}")
	private long JWT_TOKEN_VALIDITY;
	
	public String createToken(String username) {
		var expirationTime = JWT_TOKEN_VALIDITY * 1000;
		var expirationDate = new Date(System.currentTimeMillis() + expirationTime);
		Map<String, Object> claims = Map.of("name", username);
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(expirationDate)
				.addClaims(claims)
				.signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
				.compact();
	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
			String email = claims.getSubject();
			return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
		} catch (JwtException e) {
			e.printStackTrace();
			return null;
		}
	}
}
