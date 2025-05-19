package com.sid.portal_web.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sid.portal_web.error.TokenException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockitoAnnotations;

import java.util.Date;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    @Mock
    private UserDetails userDetails;

    private final String secretKey = "YmFzZTY0ZW5jb2RlZHNlY3JldGtleWZvcnRlc3Rpbmc="; // "base64encodedsecretkeyfortesting"

    @BeforeEach
    void setUp() { // TODO : usar try para cerrar automaticamente el openMocks
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtServiceImpl();
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1000 * 60 * 10L); // 10 minutes
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 1000 * 60 * 60 * 24L); // 1 day
    }

    @Test
    @DisplayName("Should generate a token and validate its claims")
    void testGenerateAndValidateToken() {
        String username = "test@example.com";
        when(userDetails.getUsername()).thenReturn(username);

        String token = jwtService.getToken(userDetails);

        assertNotNull(token, "Token should not be null");

        String extractedEmail = jwtService.getEmailFromToken(token);
        assertEquals(username, extractedEmail, "Email extracted from token should match");

        Claims claims = jwtService.getAllClaims(token);
        assertNotNull(claims.getIssuedAt(), "Should have issued date");
        assertNotNull(claims.getExpiration(), "Should have expiration date");

        boolean expired = jwtService.isExpired(token, userDetails);
        assertFalse(expired, "Newly generated token should not be expired");

        boolean refreshValid = jwtService.isRefreshTokenValid(token, userDetails);
        assertTrue(refreshValid, "Token should be valid as a newly generated refresh token");
    }

    @Test
    @DisplayName("Should throw TokenException when using an invalid token")
    void testInvalidTokenThrowsException() {
        String invalidToken = "invalid.token.value";
        when(userDetails.getUsername()).thenReturn("test@example.com");

        assertThrows(TokenException.class, () -> jwtService.getAllClaims(invalidToken));
        assertThrows(TokenException.class, () -> jwtService.getEmailFromToken(invalidToken));
        assertThrows(TokenException.class, () -> jwtService.isExpired(invalidToken, userDetails));
    }

    @Test
    @DisplayName("getClaim should return empty if token is not valid")
    void testGetClaimReturnsEmptyOptionalForInvalidToken() {
        String invalidToken = "fake.token.test";

        var result = jwtService.getClaim(invalidToken, Claims::getSubject);
        assertTrue(result.isEmpty(), "Should return empty Optional if token is invalid");
    }

    @Test
    @DisplayName("getAllClaims should throw exception if token is empty")
    void testEmptyTokenThrows() {
        assertThrows(TokenException.class, () -> jwtService.getAllClaims(""));
        assertThrows(TokenException.class, () -> jwtService.getAllClaims(null));
    }

    @Test
    @DisplayName("Token with more expiration")
    void testRefreshTokenExpiration() {
        String token = jwtService.getToken(userDetails);
        String refreshToken = jwtService.getRefreshToken(userDetails);

        Date tokenExp = jwtService.getExpiration(token);
        Date refreshExp = jwtService.getExpiration(refreshToken);

        assertTrue(refreshExp.after(tokenExp));
    }
}
