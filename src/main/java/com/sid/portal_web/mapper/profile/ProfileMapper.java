package com.sid.portal_web.mapper.profile;

import com.sid.portal_web.core.Profile;
import com.sid.portal_web.entity.ProfileEntity;
import org.springframework.stereotype.Component;


@Component
public class ProfileMapper {

    public Profile entityToDomain(ProfileEntity profile) {
        return new Profile(
                profile.getUserId(),
                profile.getName(),
                profile.getFirstLastName(),
                profile.getSecondLastName(),
                profile.getProfileImage(),
                profile.getBio(),
                profile.getAcademic_degree());
    }

    public ProfileEntity domainToEntity(Profile profile) {
        return ProfileEntity
                .builder()
                .name(profile.name())
                .firstLastName(profile.firsLastName())
                .secondLastName(profile.secondLastName())
                .academic_degree(profile.academicDegree())
                .userId(profile.id())
                .bio(profile.bio())
                .profileImage(profile.image())
                .build();
    }


}
