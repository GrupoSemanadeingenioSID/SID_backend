package com.sid.portal_web.mapper.user;


import com.sid.portal_web.core.User;
import com.sid.portal_web.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User entityToDomain(UserEntity user) {
        return new User(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getInstitutionalEmail(),
                user.isActive());
    }

    public UserEntity domainToEntity(User user) {
        return UserEntity
                .builder()
                .userId(user.id())
                .email(user.email())
                .password(passwordEncoder.encode(user.password()))
                .institutionalEmail(user.institutionalEmail())
                .build();
    }

}
