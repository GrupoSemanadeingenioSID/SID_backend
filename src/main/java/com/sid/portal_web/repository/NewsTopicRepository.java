package com.sid.portal_web.repository;

import com.sid.portal_web.entity.NewsTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsTopicRepository extends JpaRepository<NewsTopic, String> {
    // Si quieres buscar por nombre puedes hacer:
    // Optional<NewsTopic> findByName(String name);
}
