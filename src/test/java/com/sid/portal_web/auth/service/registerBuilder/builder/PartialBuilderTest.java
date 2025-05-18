package com.sid.portal_web.auth.service.registerBuilder.builder;

import com.sid.portal_web.core.Profile;
import com.sid.portal_web.core.User;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.entity.ProfileEntity;
import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.error.EmailException;
import com.sid.portal_web.mapper.profile.ProfileMapper;
import com.sid.portal_web.service.profile.ProfileService;
import com.sid.portal_web.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartialBuilderTest {

    @Mock
    private UserService userService;

    @Mock
    private ProfileService profileService;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private PartialBuilder partialBuilder;

    private RegisterRequest validRequest;
    private UserEntity mockUserEntity;
    private ProfileEntity mockProfileEntity;

    @BeforeEach
    void setUp() {
        validRequest = RegisterRequest.builder()
                .name("John")
                .first_lastName("Doe")
                .second_lastName("Smith")
                .institutionalEmail("john.doe@universidad.edu.co")
                .password("password123")
                .build();

        mockUserEntity = new UserEntity();
        mockUserEntity.setUserId(1);
        mockUserEntity.setEmail("jds@sid.com");

        mockProfileEntity = new ProfileEntity();
        mockProfileEntity.setUserId(1);
    }

    @Test
    void whenBuildUser_withValidData_thenSuccess() {
        // Given
        when(userService.save(any(User.class))).thenReturn(mockUserEntity);

        // When
        partialBuilder.setRequest(validRequest);
        partialBuilder.buildUser();

        // Then
        verify(userService).save(any(User.class));
        assertEquals(mockUserEntity, partialBuilder.getUserEntity());
    }

    @Test
    void whenBuildUser_withNullInstitutionalEmail_thenThrowException() {
        // Given
        validRequest.setInstitutionalEmail(null);
        partialBuilder.setRequest(validRequest);

        // When & Then
        assertThrows(EmailException.class, () -> partialBuilder.buildUser());
    }

    @Test
    void whenBuildProfile_withoutBuildingUser_thenThrowException() {
        // Given
        partialBuilder.setRequest(validRequest);

        // When & Then
        assertThrows(IllegalStateException.class, () -> partialBuilder.buildProfile());
    }

    @Test
    void whenBuildProfile_afterBuildingUser_thenSuccess() {
        // Given
        when(userService.save(any(User.class))).thenReturn(mockUserEntity);
        when(profileMapper.domainToEntity(any(Profile.class))).thenReturn(mockProfileEntity);
        when(profileService.save(any(ProfileEntity.class))).thenReturn(mockProfileEntity);

        // When
        partialBuilder.setRequest(validRequest)
                .buildUser()
                .buildProfile();

        // Then
        verify(profileService).save(any(ProfileEntity.class));
        verify(profileMapper).domainToEntity(any(Profile.class));
    }

    @Test
    void whenGetUserEntity_beforeBuildingUser_thenReturnNull() {
        // When
        UserEntity result = partialBuilder.getUserEntity();

        // Then
        assertNull(result);
    }

    @Test
    void whenCompleteBuilderFlow_thenSuccessfullyCreateUserAndProfile() {
        // Given
        when(userService.save(any(User.class))).thenReturn(mockUserEntity);
        when(profileMapper.domainToEntity(any(Profile.class))).thenReturn(mockProfileEntity);
        when(profileService.save(any(ProfileEntity.class))).thenReturn(mockProfileEntity);

        // When
        partialBuilder
            .setRequest(validRequest)
            .buildUser()
            .buildProfile();

        // Then
        verify(userService).save(any(User.class));
        verify(profileMapper).domainToEntity(any(Profile.class));
        verify(profileService).save(any(ProfileEntity.class));
        assertNotNull(partialBuilder.getUserEntity());
    }
}