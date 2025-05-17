package com.sid.portal_web.auth.service;


import com.sid.portal_web.auth.service.interfaces.JwtService;
import com.sid.portal_web.error.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshExpiration;


    @Override
    public String getToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, jwtExpiration);
    }

    @Override
    public String getRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    @Override
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isExpired(token));
    }

    @Override
    public boolean isExpired(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isExpired(token));
    }

    @Override
    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject)
                .orElseThrow(TokenException::invalidTokenException);
    }

    @Override
    public Claims getAllClaims(String token) {
        if (StringUtils.isBlank(token)) {
            throw TokenException.tokenNotFoundException();
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (TokenException e) {
            throw TokenException.expiredTokenException();
        } catch (Exception e) {
            throw TokenException.invalidTokenException();
        }
    }

    @Override
    public <T> Optional<T> getClaim(String token, Function<Claims, T> clazz) {
        try {
            final Claims claims = getAllClaims(token);
            return Optional.of(clazz.apply(claims));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    // - - -

    // private methods:
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration).orElse(null);
    }

    private Boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
