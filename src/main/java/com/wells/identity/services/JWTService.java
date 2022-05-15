package com.wells.identity.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wells.identity.model.UserLogin;
import com.wells.identity.request.LoginRequest;
import com.wells.identity.request.UserRegisterRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

	public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

	private String secret = "5dcd2348-1926-4eff-9939-6411afef72f2";

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.getExpiration();
	}

	public Object getClaimFromToken(String token, String key) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.get(key);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserLogin userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("empId", userDetails.getEmpid());
		claims.put("firstName", userDetails.getFirstName());
		claims.put("lastName", userDetails.getLastName());
		return doGenerateToken(claims, "");
	}
	
	public String generateTokenforRegisteration(UserRegisterRequest userRegisterRequest) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("empId", userRegisterRequest.getEmpId());
		claims.put("firstName", userRegisterRequest.getFirstName());
		claims.put("lastName", userRegisterRequest.getLastName());
		return doGenerateToken(claims, "");
	}
	
	public String generateTokenforAdmin(LoginRequest loginRequest) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("empId", loginRequest.getEmpId());
		return doGenerateToken(claims, "");
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

}
