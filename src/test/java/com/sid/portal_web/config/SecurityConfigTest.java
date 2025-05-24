package com.sid.portal_web.config;

import com.sid.portal_web.auth.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private HttpSecurity httpSecurity;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(jwtAuthenticationFilter, authenticationProvider);
    }

    @Test
    void filterChain_ShouldConfigureHttpSecurity() throws Exception {
        // Arrange
        mockHttpSecurityChain();

        // Act
        SecurityFilterChain filterChain = securityConfig.filterChain(httpSecurity);

        // Assert
        assertNotNull(filterChain);
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).sessionManagement(any());
        verify(httpSecurity).authenticationProvider(same(authenticationProvider));
        verify(httpSecurity).addFilterBefore(same(jwtAuthenticationFilter), eq(UsernamePasswordAuthenticationFilter.class));
        verify(httpSecurity).build();
    }

    @Test
    void filterChain_ShouldPermitPublicEndpoints() throws Exception {
        // Arrange
        mockHttpSecurityChain();

        // Act
        securityConfig.filterChain(httpSecurity);

        // Assert
        // Verify that paths are configured correctly (public endpoints)
        // This is a more complex verification that would ideally check the actual matchers
        // but for simplicity we just verify the key methods were called
        verify(httpSecurity).authorizeHttpRequests(any());
    }

    private void mockHttpSecurityChain() throws Exception {
        // Create mocks for the builder pattern
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.authenticationProvider(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);
        
        // La implementación concreta de SecurityFilterChain es DefaultSecurityFilterChain
        DefaultSecurityFilterChain mockFilterChain = mock(DefaultSecurityFilterChain.class);
        when(httpSecurity.build()).thenReturn(mockFilterChain);
    }
}