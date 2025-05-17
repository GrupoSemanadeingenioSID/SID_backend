package com.sid.portal_web.service.user;

import com.sid.portal_web.entity.UserEntity;

public interface UserService {

    UserEntity save(UserEntity user);
    boolean existsByEmail(String email);
}
