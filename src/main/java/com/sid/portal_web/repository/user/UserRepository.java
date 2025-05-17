package com.sid.portal_web.repository.user;

import com.sid.portal_web.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByInstitutionalEmail(String institutionalEmail);
    Optional<UserEntity> findByEmail(String email);
}
