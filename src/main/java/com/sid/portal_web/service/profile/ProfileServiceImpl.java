package com.sid.portal_web.service.profile;

import com.sid.portal_web.entity.ProfileEntity;
import com.sid.portal_web.repository.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    // este metodo no tiene error por logica de la aplicacion aun así necesario verificar si a futuro necesita o no ,
    @Override
    public ProfileEntity save(ProfileEntity profileEntity) {
        return profileRepository.save(profileEntity);
    }
}
