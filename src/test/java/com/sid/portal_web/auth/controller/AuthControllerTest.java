package com.sid.portal_web.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.portal_web.auth.service.interfaces.AuthService;
import com.sid.portal_web.dto.request.LoginRequest;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.AuthResponse;
import com.sid.portal_web.error.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Asume que tienes un handler de excepciones global
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void loginShouldReturnToken() throws Exception {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        AuthResponse expectedResponse = AuthResponse.builder()
                .token("testToken123")
                .refreshToken("refreshToken123")
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("testToken123"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken123"));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void registerShouldReturnToken() throws Exception {
        // Arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Juan")
                .first_lastName("Pérez")
                .second_lastName("García")
                .institutionalEmail("juan.perez@universidad.edu")
                .password("securePass123")
                .build();

        AuthResponse expectedResponse = AuthResponse.builder()
                .token("newUserToken")
                .refreshToken("newUserRefreshToken")
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("newUserToken"))
                .andExpect(jsonPath("$.refreshToken").value("newUserRefreshToken"));

        verify(authService).register(any(RegisterRequest.class));
    }

    @Test
    void loginShouldReturn400WithInvalidData() throws Exception {
        // Arrange - Creamos un objeto LoginRequest sin email para simular datos inválidos
        LoginRequest invalidLoginRequest = LoginRequest.builder()
                .password("password123")
                .build();

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidLoginRequest)))
                .andExpect(status().isBadRequest());

        // Verificamos que el service nunca es llamado con datos inválidos
        verify(authService, never()).login(any(LoginRequest.class));
    }

    @Test
    void registerShouldReturn400WithInvalidData() throws Exception {
        // Arrange - Creamos un objeto RegisterRequest incompleto
        RegisterRequest invalidRegisterRequest = RegisterRequest.builder()
                .name("Juan")
                // Sin apellidos ni email
                .password("password123")
                .build();

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegisterRequest)))
                .andExpect(status().isBadRequest());

        // Verificamos que el service nunca es llamado con datos inválidos
        verify(authService, never()).register(any(RegisterRequest.class));
    }

    @Test
    void loginShouldHandleServiceException() throws Exception {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("wrongPassword")
                .build();

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError());

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void registerShouldHandleServiceException() throws Exception {
        // Arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Juan")
                .first_lastName("Pérez")
                .second_lastName("García")
                .institutionalEmail("existing.user@universidad.edu") // Email que ya existe
                .password("securePass123")
                .build();

        when(authService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Email already registered"));

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isInternalServerError());

        verify(authService).register(any(RegisterRequest.class));
    }
}