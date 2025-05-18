package com.sid.portal_web.auth.jwt;

import com.sid.portal_web.auth.service.interfaces.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @Test
    void whenValidToken_thenSetsAuthentication() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtService.getEmailFromToken("validToken")).thenReturn("user@example.com");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtService.isExpired("validToken", userDetails)).thenReturn(false);

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // Limpiamos el contexto de seguridad después del test
        SecurityContextHolder.clearContext();
    }

    @Test
    void whenNoAuthorizationHeader_thenDoFilter() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void whenExpiredToken_thenDoesNotAuthenticate() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtService.getEmailFromToken("validToken")).thenReturn("user@example.com");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtService.isExpired("validToken", userDetails)).thenReturn(true);

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void whenInvalidTokenFormat_thenReturns401() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat");

        // Mockea el PrintWriter para evitar NullPointerException
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(writer).write(anyString());  // Verifica que se escribió el JSON de error
        verify(filterChain, never()).doFilter(request, response);
    }

    @AfterEach
    void tearDown() {
        // Limpiamos el contexto de seguridad después de cada test
        SecurityContextHolder.clearContext();
    }
}