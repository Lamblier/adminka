package ru.nonoka.securityadminka.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    String jwtSecret;

    @Value("${jwt.lifetime}")
    Duration jwtLifetime;

    public String createToken(UserDetails userDetails) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(HS256, jwtSecret)
                .compact();
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}
