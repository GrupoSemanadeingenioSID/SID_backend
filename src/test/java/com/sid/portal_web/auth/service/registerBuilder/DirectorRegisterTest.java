package com.sid.portal_web.auth.service.registerBuilder;

import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DirectorRegisterTest {

    @Mock
    private RegisterBuilder registerBuilder;

    @InjectMocks
    private DirectorRegister directorRegister;

    private RegisterRequest mockRequest;
    private UserEntity mockUserEntity;

    @BeforeEach
    void setUp() {
        mockRequest = RegisterRequest.builder()
                .name("John")
                .first_lastName("Doe")
                .second_lastName("Smith")
                .institutionalEmail("john.doe@universidad.edu.co")
                .password("password123")
                .build();

        mockUserEntity = new UserEntity();
        mockUserEntity.setUserId(1);
        mockUserEntity.setEmail("jds@sid.com");
    }

    @Test
    void whenRegister_thenExecuteAllBuilderSteps() {
        // Given
        when(registerBuilder.setRequest(mockRequest)).thenReturn(registerBuilder);
        when(registerBuilder.buildUser()).thenReturn(registerBuilder);
        when(registerBuilder.buildProfile()).thenReturn(registerBuilder);

        // When
        directorRegister.register(mockRequest);

        // Then
        verify(registerBuilder).setRequest(mockRequest);
        verify(registerBuilder).buildUser();
        verify(registerBuilder).buildProfile();
    }

    @Test
    void whenGetUser_thenReturnUserEntity() {
        // Given
        when(registerBuilder.getUserEntity()).thenReturn(mockUserEntity);

        // When
        UserEntity result = directorRegister.getUser();

        // Then
        assertNotNull(result);
        assertEquals(mockUserEntity, result);
        verify(registerBuilder).getUserEntity();
    }

    @Test
    void whenRegister_thenExecuteStepsInCorrectOrder() {
        // Given
        when(registerBuilder.setRequest(any())).thenReturn(registerBuilder);
        when(registerBuilder.buildUser()).thenReturn(registerBuilder);
        when(registerBuilder.buildProfile()).thenReturn(registerBuilder);

        // When
        directorRegister.register(mockRequest);

        // Then
        var inOrder = inOrder(registerBuilder);
        inOrder.verify(registerBuilder).setRequest(mockRequest);
        inOrder.verify(registerBuilder).buildUser();
        inOrder.verify(registerBuilder).buildProfile();
    }

    @Test
    void whenRegisterFails_thenPropagateException() {
        // Given
        when(registerBuilder.setRequest(mockRequest)).thenThrow(new IllegalStateException("Registration failed"));

        // When & Then
        assertThrows(IllegalStateException.class, () -> directorRegister.register(mockRequest));
    }

    @Test
    void whenGetUserWithNoRegistration_thenReturnNull() {
        // Given
        when(registerBuilder.getUserEntity()).thenReturn(null);

        // When
        UserEntity result = directorRegister.getUser();

        // Then
        assertNull(result);
        verify(registerBuilder).getUserEntity();
    }
    @Test
    void register_ThenGetUser_ShouldReturnCreatedUser() {
        // Given
        when(registerBuilder.setRequest(mockRequest)).thenReturn(registerBuilder);
        when(registerBuilder.buildUser()).thenReturn(registerBuilder);
        when(registerBuilder.buildProfile()).thenReturn(registerBuilder);
        when(registerBuilder.getUserEntity()).thenReturn(mockUserEntity);

        // When
        directorRegister.register(mockRequest);
        UserEntity result = directorRegister.getUser();

        // Then
        assertEquals(mockUserEntity, result);
    }
}