package com.sid.portal_web.service.user;

import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.error.UserException;
import com.sid.portal_web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;

    @Override
    public UserEntity save(UserEntity user) {
        if(repository.existsByEmail(user.getEmail())) {
            throw UserException.userAlreadyExists();
        }
        return repository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
