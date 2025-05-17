package com.sid.portal_web.auth.service.registerBuilder.builder;

import com.sid.portal_web.auth.service.registerBuilder.RegisterBuilder;
import com.sid.portal_web.core.Profile;
import com.sid.portal_web.core.User;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.entity.ProfileEntity;
import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.error.EmailException;
import com.sid.portal_web.mapper.profile.ProfileMapper;
import com.sid.portal_web.service.profile.ProfileService;
import com.sid.portal_web.service.user.UserService;
import com.sid.portal_web.utils.SidEmailBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PartialBuilder extends RegisterBuilder {
    private RegisterRequest registerRequest;
    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private UserEntity savedUser; // Estado temporal del usuario creado

    @Autowired
    public PartialBuilder(UserService userService,
                          ProfileService profileService,
                          ProfileMapper profileMapper) {
        this.userService = userService;
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    @Override
    public RegisterBuilder setRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
        return this;
    }

    @Override
    @Transactional
    public RegisterBuilder buildUser() {
        String email = this.getSidEmail();
        if (registerRequest.getInstitutionalEmail() == null) {
            throw EmailException.blankEducationalEmailAddressException();
        }

        User user = new User(null, email, registerRequest.getPassword(),
                registerRequest.getInstitutionalEmail(), true); // cuando se inicia será true
        this.savedUser = userService.save(user);
        return this;
    }

    @Override
    @Transactional
    public RegisterBuilder buildProfile() {
        if (this.savedUser == null) {
            throw new IllegalStateException("User must be saved before creating profile");
        }
        Profile profile = new Profile(
                null,
                registerRequest.getName(),
                registerRequest.getFirst_lastName(),
                registerRequest.getSecond_lastName(),
                null, // image
                null, // bio
                null  // academicDegree
        );

        ProfileEntity profileEntity = profileMapper.domainToEntity(profile);
        profileEntity.setUser(savedUser);
        profileService.save(profileEntity);
        return this;
    }

    @Override
    public UserEntity getUserEntity() {
        return this.savedUser;
    }

    private String getSidEmail() {
        return SidEmailBuilder.withEmail(
                registerRequest.getName(),
                registerRequest.getFirst_lastName(),
                registerRequest.getSecond_lastName(),
                registerRequest.getInstitutionalEmail()
        );
    }
}
