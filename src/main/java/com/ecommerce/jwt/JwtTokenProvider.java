/**
 * 
 */
package com.ecommerce.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecommerce.userdetails.CustomUserDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * 
 */
@Component
public class JwtTokenProvider {

	private static final Logger logger = Logger.getLogger(JwtTokenProvider.class.getName());

	private String secretKey;

	private long validityInMilliseconds;

	/**
	 * @param secretKey
	 * @param validityInMilliseconds
	 */
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
			@Value("${jwt.expiration}") long validityInMilliseconds) {
		this.secretKey = secretKey;
		this.validityInMilliseconds = validityInMilliseconds;
	}

	// Generate token for user
	public String generateRefreshToken(CustomUserDetail userDetail) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", userDetail.getId());
		claims.put("roles", userDetail.getAuthorities());
		return createRefreshToken(claims, userDetail.getUsername());
	}

	public String generateAccessToken(CustomUserDetail userDetail) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", userDetail.getId());
		claims.put("roles", userDetail.getAuthorities());
		return createAccessToken(claims, userDetail.getUsername());
	}

	private String createRefreshToken(Map<String, Object> claims, String subject) {

//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
//				.signWith(SignatureAlgorithm.HS256, secretKey).compact();

		return Jwts.builder().claims(claims).id(String.valueOf(UUID.randomUUID()))
				.issuedAt(new Date(System.currentTimeMillis()))

				.expiration(new Date(System.currentTimeMillis() + validityInMilliseconds)).subject(subject)
				// .encryptWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
				// Jwts.ENC.A128CBC_HS256)
				// .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), Jwts.SIG.HS512)
				.encryptWith(new SecretKeySpec(secretKey.getBytes(), "AES"), Jwts.ENC.A256CBC_HS512).compact();

	}

	private String createAccessToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().claims(claims).id(String.valueOf(UUID.randomUUID()))
				.issuedAt(new Date(System.currentTimeMillis()))

				.expiration(new Date(System.currentTimeMillis() + validityInMilliseconds)).subject(subject)

				.encryptWith(new SecretKeySpec(secretKey.getBytes(), "AES"), Jwts.ENC.A256CBC_HS512).compact();

	}

	// Validate token
	public boolean validateToken(String token, String user) {
		final String username = extractUsername(token);
		return username == null ? false : (username.equals(user) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extract userId from token
	public Long extractUserId(String token) {
		final Claims claims = extractAllClaims(token);

		if (claims != null && claims.containsKey("id")) {
			logger.info("claims is for userid---> " + claims);
			logger.info("claims is userId---> " + claims.get("id"));
			// User user = claims.get("user", User.class);
			return (Long) claims.get("id");
		} else {
			logger.info("user id not exist");
		}
		return null;
	}

	// Extract userId from token
	@SuppressWarnings("unchecked")
	public List<String> extractRoles(String token) {
		final Claims claims = extractAllClaims(token);

		if (claims != null && claims.containsKey("roles")) {
			logger.info("claims is for roles---> " + claims);
			logger.info("claims is roles---> " + claims.get("roles"));
			// User user = claims.get("user", User.class);
			return (List<String>) claims.get("roles");
		} else {
			logger.info("roles not exist");
		}
		return null;
	}

	public Long extractId(String token) {
		return extractClaim(token, Claims::getId) == null ? null : Long.valueOf(extractClaim(token, Claims::getId));
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extract expiration date from token
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);

		return claims == null ? null : claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		try {
			JwtParser jwtParser = Jwts.parser()
					// .enc().add(Jwts.ENC.A128CBC_HS256).and().sig().add(Jwts.SIG.HS512).and()
					.decryptWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
					// .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
					.build();

			Jwe<Claims> jwe = jwtParser.parseEncryptedClaims(token);
			logger.info("jwe -->" + jwe);
			return jwe.getPayload();

		} catch (Exception e) {
			logger.warning("jwe is not instance of claims " + e.getMessage());
		}
		return null;

	}
}
