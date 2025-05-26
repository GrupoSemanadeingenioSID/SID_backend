package com.sid.portal_web.repository.activity;

import com.sid.portal_web.core.Activities;
import com.sid.portal_web.entity.activity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {

    // Consulta para obtener solo el manager principal (para ActivityWithDetails)
    @Query(value = """
        SELECT at.description AS title_description
        FROM activities a
        JOIN activity_participation ap ON a.activity_id = ap.activity_id
        JOIN activity_title at ON ap.title_id = at.title_id
        """, nativeQuery = true)
    Optional<Activities> findManager(@Param("id") Long id);

}