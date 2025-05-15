package com.sid.portal_web.auth.service;


import com.sid.portal_web.auth.service.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {





    @Override
    public String getToken(UserDetails userDetails) {
        return "";
    }

    @Override
    public String getRefreshToken(UserDetails userDetails) {
        return "";
    }

    @Override
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public boolean isExpired(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String getEmailFromToken(String token) {
        return "";
    }

    @Override
    public Claims getAllClaims(String token) {
        return null;
    }

    @Override
    public <T> Optional<T> getClaim(String token, Function<Claims, T> clazz) {
        return Optional.empty();
    }
}
