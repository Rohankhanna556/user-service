package com.rohanai.user.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class JwtTokenUtil {
    private final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    // Access token validity (1 hour)
    private final long tokenValidity = 3600000; // 1 hour

//    private final long tokenValidity = 60000;

    // Refresh token validity (24 hours)
    private final long refreshTokenValidity = 86400000; // 24 hours

    // Generate an access token for a specific email or username
    public String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Generate a refresh token (longer validity)
    public String generateRefreshToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidity);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Validate the refresh token (can be reused if the refresh token hasn't expired)
    public boolean validateRefreshToken(String refreshToken) {
        return !isTokenExpired(refreshToken);
    }

    // Refresh the access token using the refresh token
    public String refreshToken(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            String username = getUsernameFromToken(refreshToken);
            return generateToken(username); // Generate a new access token
        }
        throw new RuntimeException("Invalid or expired refresh token.");
    }


    //for login generation of token based on usertype and email
    public String generateTokenLogin(String subject, String userType) {
         Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setSubject(subject)
                .claim("role", userType)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    //extract userType from token
    public String getUserTypeFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("role", String.class);
    }


    // Extract username from the token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Extract all claims from the token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }
}