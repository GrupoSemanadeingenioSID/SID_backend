package com.sid.portal_web.mapper.userWithProfile;


import com.sid.portal_web.core.Profile;
import com.sid.portal_web.core.User;
import com.sid.portal_web.core.UserWithProfile;
import com.sid.portal_web.dto.response.UserWithProfileResponse;
import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.mapper.profile.ProfileMapper;
import com.sid.portal_web.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserWithProfileMapper {

    private final UserMapper userMapper = new UserMapper();
    private final ProfileMapper profileMapper = new ProfileMapper();

    public UserWithProfile entityToCore(UserEntity userEntity) {

        User user = userMapper.entityToDomain(userEntity);
        Profile profile = profileMapper.entityToDomain(userEntity.getProfile());

        return new UserWithProfile(user, profile);
    }

    public UserWithProfileResponse domainToResponse(UserWithProfile userWithProfile) {
        return UserWithProfileResponse
                .builder()
                .id(userWithProfile.user().id())
                .email(userWithProfile.user().email())
                .name(userWithProfile.profile().name())
                .image(userWithProfile.profile().image())
                .build();
    }


}
