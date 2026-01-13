package com.adam.opsflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24h

    private Claims validateAndGetClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(UUID userId, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }


    public UUID getUserId(String token) {
        return UUID.fromString(validateAndGetClaims(token).getSubject());
    }

    public String getRole(String token) {
        return validateAndGetClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token){
        try {
            validateAndGetClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
