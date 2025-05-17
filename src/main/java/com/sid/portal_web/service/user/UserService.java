package com.sid.portal_web.service.user;

import com.sid.portal_web.core.User;
import com.sid.portal_web.entity.UserEntity;

public interface UserService {

    UserEntity save(User user);
    UserEntity saveUpdate(UserEntity user);
    boolean existsByInstitutionalEmail(String email);
}
