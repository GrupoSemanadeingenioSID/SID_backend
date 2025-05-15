package com.sid.portal_web.mapper.user;


import com.sid.portal_web.core.User;
import com.sid.portal_web.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User entityToDomain(UserEntity user) {
        return new User(user.getUserId(), user.getEmail(), user.getPassword());
    }

}
