package com.sid.portal_web.repository;

import com.sid.portal_web.entity.News.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    // Método mágico de Spring para buscar por título sin importar mayúsculas
    List<News> findByTitleContainingIgnoreCase(String keyword);
}
