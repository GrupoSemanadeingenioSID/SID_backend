package com.sid.portal_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sid.portal_web.entity.News.NewsTopic;

@Repository
public interface NewsTopicRepository extends JpaRepository<NewsTopic, String> {
    // Si quieres buscar por nombre puedes hacer:
    // Optional<NewsTopic> findByName(String name);
}
