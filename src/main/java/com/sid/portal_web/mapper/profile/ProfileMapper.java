package com.sid.portal_web.mapper.profile;

import com.sid.portal_web.core.Profile;
import com.sid.portal_web.entity.ProfileEntity;

public class ProfileMapper {

    public Profile entityToDomain(ProfileEntity profile) {
        return new Profile(
                profile.getUserId(),
                profile.getName(),
                profile.getProfile_image(),
                profile.getBio(),
                profile.getAcademic_degree());
    }

    //public ProfileRespon


}
