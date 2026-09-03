package com.catmanscodes.securityapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JWTService {

    private static final String SECRET = "mySuperSecretKeyForJwtSigningMustBeAtLeast64CharactersLong123456789";

    public String generateToken(String username, String role) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("role", "ROLE_" + role);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .addClaims(claims)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // get subject -username from token

    public String extractUserNameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // get expiration date from token
    public Date extractExpirationFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    // validate expiration time
    public boolean isTokenValid(String token) {

        return !extractExpirationFromToken(token)
                .before(new Date());
    }

}
