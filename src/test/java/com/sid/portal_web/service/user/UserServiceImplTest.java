package com.sid.portal_web.service.user;

import com.sid.portal_web.core.User;
import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.error.UserException;
import com.sid.portal_web.mapper.user.UserMapper;
import com.sid.portal_web.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks private UserServiceImpl userService;

    private User testUser;
    private UserEntity testUserEntity;
    private final String TEST_EMAIL = "jds@sid.com";
    private final String TEST_INSTITUTIONAL_EMAIL = "john.doe@universidad.edu.co";
    private final String TEST_PASSWORD = "password123";

    @BeforeEach
    void setUp() {
        testUser = new User(null, TEST_EMAIL, TEST_PASSWORD, TEST_INSTITUTIONAL_EMAIL, true);

        testUserEntity = new UserEntity();
        testUserEntity.setUserId(1);
        testUserEntity.setEmail(TEST_EMAIL);
        testUserEntity.setPassword("encodedPassword");
        testUserEntity.setInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL);
        testUserEntity.setActive(true);
    }

    @AfterEach
    void tearDown() {
        reset(userRepository, userMapper);
    }

    @Test
    void save_WhenUserDoesNotExist_ShouldSaveAndReturnUser() {
        when(userRepository.findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL))
                .thenReturn(Optional.empty());
        when(userMapper.domainToEntity(testUser)).thenReturn(testUserEntity);
        when(userRepository.save(testUserEntity)).thenReturn(testUserEntity);

        UserEntity savedUser = userService.save(testUser);

        assertNotNull(savedUser);
        assertEquals(1, savedUser.getUserId());
        verify(userRepository).findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL);
        verify(userMapper).domainToEntity(testUser);
        verify(userRepository).save(testUserEntity);
    }

    @Test
    void save_WhenUserAlreadyExists_ShouldThrowUserException() {
        when(userRepository.findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL))
                .thenReturn(Optional.of(testUserEntity));

        assertThrows(UserException.class, () -> userService.save(testUser));

        verify(userRepository).findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL);
        verifyNoInteractions(userMapper);
        verify(userRepository, never()).save(any());
    }

    @Test
    void existsByInstitutionalEmail_WhenUserExists_ShouldReturnTrue() {
        when(userRepository.findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL))
                .thenReturn(Optional.of(testUserEntity));

        assertTrue(userService.existsByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL));
        verify(userRepository).findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL);
    }

    @Test
    void existsByInstitutionalEmail_WhenUserDoesNotExist_ShouldReturnFalse() {
        when(userRepository.findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL))
                .thenReturn(Optional.empty());

        assertFalse(userService.existsByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL));
        verify(userRepository).findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL);
    }

    @Test
    void update_ShouldReturnNullAsItIsNotImplementedYet() {
        assertNull(userService.update(testUserEntity));
        verifyNoInteractions(userRepository);
    }

    @Test
    void save_WhenRepositoryThrowsException_ShouldPropagateException() {
        when(userRepository.findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL))
                .thenReturn(Optional.empty());
        when(userMapper.domainToEntity(testUser)).thenReturn(testUserEntity);
        when(userRepository.save(testUserEntity)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.save(testUser));

        verify(userRepository).findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL);
        verify(userMapper).domainToEntity(testUser);
        verify(userRepository).save(testUserEntity);
    }

    @Test
    void save_EnsuresUserIsSetToActive() {
        User inactiveUser = new User(null, TEST_EMAIL, TEST_PASSWORD, TEST_INSTITUTIONAL_EMAIL, false);
        UserEntity mappedEntity = new UserEntity();
        mappedEntity.setActive(false);

        when(userRepository.findByInstitutionalEmail(TEST_INSTITUTIONAL_EMAIL))
                .thenReturn(Optional.empty());
        when(userMapper.domainToEntity(inactiveUser)).thenReturn(mappedEntity);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity savedUser = userService.save(inactiveUser);

        assertTrue(savedUser.isActive());
        verify(userRepository).save(argThat(UserEntity::isActive));
    }
}