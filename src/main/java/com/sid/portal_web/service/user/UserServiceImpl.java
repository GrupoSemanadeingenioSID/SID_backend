package com.sid.portal_web.service.user;

import com.sid.portal_web.core.User;
import com.sid.portal_web.entity.UserEntity;
import com.sid.portal_web.error.UserException;
import com.sid.portal_web.mapper.user.UserMapper;
import com.sid.portal_web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public UserEntity save(User userCore) {
        if (this.existsByInstitutionalEmail(userCore.institutionalEmail())) {
            throw UserException.userAlreadyExists();
        }
        UserEntity entity = userMapper.domainToEntity(userCore);
        entity.setActive(true);
        return repository.save(entity);
    }

    // Aqui lo cambiaremos para dividir cuando se guarda de cuando se hace un update
    @Override
    public UserEntity update(UserEntity user) {
        return null;
    }

    @Override
    public boolean existsByInstitutionalEmail(String email) {
        return repository.findByInstitutionalEmail(email).isPresent();
    }
}
