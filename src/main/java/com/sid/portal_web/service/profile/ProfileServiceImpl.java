package com.sid.portal_web.service.profile;

import com.sid.portal_web.entity.ProfileEntity;
import com.sid.portal_web.repository.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileEntity save(ProfileEntity profileEntity) {
        log.info("Saving profile {}", profileEntity);
        return profileRepository.save(profileEntity);
    }
}
