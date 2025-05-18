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
        UserEntity entity = userMapper.domainToEntity(userCore);

        if (this.existsByInstitutionalEmail(entity.getEmail())) {
            throw UserException.userAlreadyExists();
        }
        return repository.save(entity);
    }

    // Aqui lo cambiaremos para dividir cuando se guarda de cuand
    @Override
    public UserEntity saveUpdate(UserEntity user) {
        return null;
    }

    @Override
    public boolean existsByInstitutionalEmail(String email) {
        return repository.findByInstitutionalEmail(email).isPresent();
    }
}
