package com.sid.portal_web.auth.service.registerBuilder;


import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DirectorRegister {

    private final RegisterBuilder registerBuilder;

    public void register(RegisterRequest request){
        registerBuilder.setRequest(request)
                .buildUser()
                .buildProfile();
    }

    public UserEntity getUser(){
        return registerBuilder.getUserEntity();
    }}
