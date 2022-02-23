package com.jeanbarcellos.demo.application.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static String USER_NAME = "user_name";
    private static String USER_ROLES = "roles";

    @Value("${app-config.jwt.secret}")
    private String secret;

    @Value("${app-config.jwt.expiration}")
    private Integer expiration;

    public String generateToken(UserDetails user) {
        return Jwts
                .builder()
                .setClaims(this.generateClaims(user))
                .setSubject(user.getUsername())
                .setIssuedAt(this.generateIssuedAt())
                .setExpiration(this.generateExpiration())
                .setNotBefore(this.generateNotBefore())
                .signWith(this.generateKey())
                .compact();
    }

    private Map<String, Object> generateClaims(UserDetails user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(USER_NAME, user.getUsername());
        claims.put(USER_ROLES, user.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList()));

        return claims;
    }

    private Date generateIssuedAt() {
        return Date.from(
                LocalDateTime.now()
                        .atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date generateExpiration() {
        return Date.from(
                LocalDateTime.now()
                        .plusMinutes(expiration)
                        .atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date generateNotBefore() {
        return Date.from(
                LocalDateTime.now()
                        .plusSeconds(-1)
                        .atZone(ZoneId.systemDefault()).toInstant());
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

}
