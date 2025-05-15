package com.sid.portal_web.service.user;

import com.sid.portal_web.core.User;
import com.sid.portal_web.core.UserWithProfile;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.UserWithProfileResponse;
import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.mapper.userWithProfile.UserWithProfileMapper;
import com.sid.portal_web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserWithProfileWithProfileServiceImpl implements UserWithProfileService {

    private final UserRepository userRepository;
    private final UserWithProfileMapper userWithProfileMapper;

    @Override
    public List<UserWithProfileResponse> findAll() {

        List<UserEntity> users = userRepository.findAll();

        List<UserWithProfile> uwp = users
                .stream()
                .map(userWithProfileMapper::entityToCore)
                .toList();

        return uwp.stream().map(userWithProfileMapper::domainToResponse).toList();
    }

    @Override
    public Optional<UserWithProfileResponse> getById(int id) {
        return Optional.empty();
    }

    @Override
    public User save(RegisterRequest user) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
