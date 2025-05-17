package com.sid.portal_web.auth.service.registerBuilder;

import com.sid.portal_web.entity.ProfileEntity;
import com.sid.portal_web.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRegister {

    private UserEntity user;
    private ProfileEntity profile;
}
