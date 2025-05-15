package com.sid.portal_web.auth.service.interfaces;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.function.Function;

public interface JwtService {

    String getToken(UserDetails userDetails);

    String getRefreshToken(UserDetails userDetails);

    boolean isRefreshTokenValid(String token,UserDetails userDetails);

    boolean isExpired(String token,UserDetails userDetails);

    String getEmailFromToken(String token);

    Claims getAllClaims(String token);

    <T> Optional<T> getClaim(String token, Function<Claims,T> clazz);
}
