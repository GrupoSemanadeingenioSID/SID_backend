package com.sid.portal_web.auth.service.registerBuilder.builder;

import com.sid.portal_web.auth.service.registerBuilder.RegisterBuilder;
import com.sid.portal_web.auth.service.registerBuilder.ProcessRegister;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.entity.ProfileEntity;
import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.error.UserException;
import com.sid.portal_web.service.profile.ProfileService;
import com.sid.portal_web.service.user.UserService;
import com.sid.portal_web.utils.SidEmailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PartialBuilder extends RegisterBuilder {

    private RegisterRequest registerRequest;
    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public PartialBuilder(UserService userService, ProfileService profileService) {
        super.processRegister = new ProcessRegister();
        this.userService = userService;
        this.profileService = profileService;
    }

    @Override
    public RegisterBuilder setRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
        return this;
    }

    @Override
    public RegisterBuilder buildUser() {
        String email = this.getSidEmail();
        processRegister.setUser(
                UserEntity
                        .builder()
                        .password(registerRequest.getPassword())
                        .institutionalEmail(registerRequest.getInstitutionalEmail())
                        .email(email)
                        .build()
        );
        if (userService.existsByEmail(email)) {
            throw UserException.userAlreadyExists();
        }
        userService.save(processRegister.getUser());
        return this;
    }

    @Override
    public RegisterBuilder buildProfile() {
        processRegister.setProfile(
                ProfileEntity
                        .builder()
                        .name(registerRequest.getName())
                        .firstLastName(registerRequest.getFirst_lastName())
                        .secondLastName(registerRequest.getSecond_lastName())
                        .user(processRegister.getUser()) // aqui añadimos el usuario que en teoría deberia estar ya existiendo
                        .build()
        );
        profileService.save(processRegister.getProfile());
        return this;
    }

    @Override
    public UserEntity getUserEntity() {
        return super.processRegister.getUser();
    }

    private String getSidEmail() {
        return SidEmailBuilder
                .withEmail(
                        registerRequest.getName(),
                        registerRequest.getFirst_lastName(),
                        registerRequest.getSecond_lastName(),
                        registerRequest.getInstitutionalEmail()
                );

    }
}
