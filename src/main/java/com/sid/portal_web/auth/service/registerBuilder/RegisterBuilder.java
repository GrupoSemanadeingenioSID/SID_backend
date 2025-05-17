package com.sid.portal_web.auth.service.registerBuilder;

import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.entity.UserEntity;

public abstract class RegisterBuilder {

    protected ProcessRegister processRegister;

    public abstract RegisterBuilder setRequest(RegisterRequest registerRequest);
    public abstract RegisterBuilder buildUser();
    public abstract RegisterBuilder buildProfile();
    public abstract UserEntity getUserEntity();

}
