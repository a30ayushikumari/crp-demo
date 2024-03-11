package com.demoProject.crp.SecurityConfig.Util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtils {
    private JwtUtils(){}
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    public  boolean validateToken(String jwtToken) {
        return parseToken(jwtToken) != null;
    }

    private static final String ISSUER = "com.demoProject.crp";

    private Optional<Claims> parseToken(String jwtToken) {
        var jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken)
                    .getPayload());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Jwt Exception occured");
        }
        return Optional.empty();
    }

    public  Optional<String> getUsernameFromToken(String jwtToken) {
        var claimsOptional =parseToken(jwtToken);

        if(claimsOptional.isPresent()){
            return Optional.of(claimsOptional.get().getSubject());
        }
        return Optional.empty();
    }
    public String generateToken(String username) {
        var currentDate = new Date();
        var jwtTokenExpirationInMinute = 8;
        var tokenExpiration = DateUtils.addMinutes(currentDate, jwtTokenExpirationInMinute);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .signWith(secretKey)
                .issuedAt(currentDate)
                .expiration(tokenExpiration)
                .compact();
    }

    public String generateRefreshToken(String username) {
        var currentDate = new Date();
        var jwtTokenExpirationInMinute = 8;
        var tokenExpiration = DateUtils.addMinutes(currentDate, jwtTokenExpirationInMinute);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .signWith(secretKey)
                .issuedAt(currentDate)
                .expiration(tokenExpiration)
                .compact();
    }
}
