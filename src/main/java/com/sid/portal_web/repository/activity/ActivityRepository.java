package com.sid.portal_web.repository.activity;

import com.sid.portal_web.core.Activities;
import com.sid.portal_web.entity.activity.ActivityEntity;
import com.sid.portal_web.entity.activity.projection.ActivityWithManagerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {


    // Consulta para obtener el manager de una actividad específica
    @Query(value = """
        SELECT at.description AS managerName
        FROM activities a
        JOIN activity_participation ap ON a.activity_id = ap.activity_id
        JOIN activity_title at ON ap.title_id = at.title_id
        WHERE a.activity_id = :activityId 
        AND at.description LIKE '%Manager%'
        LIMIT 1
        """, nativeQuery = true)
    Optional<String> findManagerNameByActivityId(@Param("activityId") Integer activityId);



    // Consulta optimizada para obtener todas las actividades con sus managers
    @Query(value = """

            SELECT
            a.activity_id as activityId,
            a.title,
            a.description,
            UPPER(a.priority) as priority,
            UPPER(a.status) as status,
            a.total_hours as totalHours,
            a.start_date as startDate,
            a.completion_date as completionDate,
            (
                SELECT am.name AS nombre_miembro
                		FROM activity_participation ap
                		JOIN activity_title at ON ap.title_id = at.title_id
                		JOIN activity_members am ON ap.activity_member = am.activity_member
                		JOIN activities a ON ap.activity_id = a.activity_id
                		WHERE LOWER(at.description) = 'líder'
                		LIMIT 1
                
            ) as managerName
            FROM activities a""", nativeQuery = true)
    Page<ActivityWithManagerProjection> findAllActivitiesWithManager(Pageable pageable);
}