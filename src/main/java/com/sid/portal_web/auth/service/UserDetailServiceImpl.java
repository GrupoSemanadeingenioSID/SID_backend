package com.sid.portal_web.auth.service;

import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.error.UserException;
import com.sid.portal_web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = userService.findByEmail(username)
                .orElseThrow(UserException::userNotFound);


        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
