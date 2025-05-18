package com.sid.portal_web.auth.service;

import com.sid.portal_web.auth.service.interfaces.JwtService;
import com.sid.portal_web.auth.service.registerBuilder.DirectorRegister;
import com.sid.portal_web.dto.request.LoginRequest;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.AuthResponse;
import com.sid.portal_web.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private DirectorRegister directorRegister;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserDetails userDetails;
    private final String email = "test@example.com";
    private final String password = "password123";

    @BeforeEach
    void setUp() {
        userDetails = User.builder()
                .username(email)
                .password(password)
                .roles("USER")
                .build();
    }

    @Test
    @DisplayName("Debería devolver un token válido al hacer login con credenciales correctas")
    void login_shouldReturnAuthResponse_whenCredentialsAreValid() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest(email, password);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtService.getToken(userDetails)).thenReturn("jwt-token");

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).getToken(userDetails);
    }

    @Test
    @DisplayName("Debería registrar un usuario y devolver tokens válidos")
    void register_shouldReturnAuthResponseWithTokenAndRefreshToken() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        when(directorRegister.getUser()).thenReturn(userEntity);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(jwtService.getToken(userDetails)).thenReturn("jwt-token");
        when(jwtService.getRefreshToken(userDetails)).thenReturn("refresh-token");

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("refresh-token", response.getRefreshToken());

        verify(directorRegister).register(registerRequest);
        verify(userDetailsService).loadUserByUsername(email);
        verify(jwtService).getToken(userDetails);
        verify(jwtService).getRefreshToken(userDetails);
    }
}
