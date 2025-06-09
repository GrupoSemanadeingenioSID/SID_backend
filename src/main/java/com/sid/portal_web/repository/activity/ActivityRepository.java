package com.sid.portal_web.repository.activity;

import com.sid.portal_web.core.Activities;
import com.sid.portal_web.entity.activity.ActivityEntity;
import com.sid.portal_web.entity.activity.projection.ActivityByIdProjection;
import com.sid.portal_web.entity.activity.projection.ActivityWithManagerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {


    // Consulta para obtener una actividad específica
    @Query(value = """

            SELECT
    a.activity_id AS activityId,
    a.title,
    a.description,
    UPPER(a.priority) AS priority,
    UPPER(a.status) AS status,
    a.total_hours AS totalHours,
    a.start_date AS startDate,
    a.completion_date AS completionDate,

    -- Subconsulta para traer el nombre del líder
    (
        SELECT am.name
        FROM activity_participation ap
        JOIN activity_members am ON ap.activity_member = am.activity_member
        JOIN activity_title at ON ap.title_id = at.title_id
        WHERE
            LOWER(at.description) = 'líder'
            AND ap.activity_id = a.activity_id
        LIMIT 1
    ) AS managerName,

    -- Subconsulta como JSON agregando los miembros
    (
        SELECT json_agg(json_build_object(
            'name', am.name,
            'identify', am."id",  -- Nota las comillas dobles
            'isMember', ap.ismember
        ))
        FROM activity_participation ap
        JOIN activity_members am ON ap.activity_member = am.activity_member
        WHERE ap.activity_id = a.activity_id
    ) AS members,

    -- Subconsulta como JSON agregando los comités asociados
    (
        SELECT json_agg(json_build_object(
            'name', c.name,
            'leader', (
                SELECT p.name
                FROM committee_member cm
                JOIN committee_title ct ON cm.committee_title_id = ct.committee_title_id
                JOIN members m ON cm.member_id = m.member_id
                JOIN users u ON m.user_id = u.user_id
                JOIN profile p ON u.user_id = p.user_id
                WHERE cm.committee_id = c.committee_id
                AND cm.active = true
                AND (LOWER(ct.title) LIKE '%líder%' OR LOWER(ct.title) LIKE '%presidente%' OR LOWER(ct.title) LIKE '%director%')
                LIMIT 1
            ),
			'description', c.description
        ))
        FROM activity_dev ad
        JOIN committee c ON ad.committee_id = c.committee_id
        WHERE ad.activity_id = a.activity_id
    ) AS committees

FROM activities a
WHERE a.activity_id = :activityById;
               
        """, nativeQuery = true)
    Optional<ActivityByIdProjection> findActivityById(@Param("activityById") Integer activityById);



    // Consulta para obtener todas las actividades con sus managers
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
            STRING_AGG(am.name, ', ') AS managerName
        FROM
            activities a
        LEFT JOIN activity_participation ap ON ap.activity_id = a.activity_id
        LEFT JOIN activity_members am ON ap.activity_member = am.activity_member
        LEFT JOIN activity_title at ON ap.title_id = at.title_id
        WHERE LOWER(at.description) = 'líder'
        GROUP BY a.activity_id, 
                a.title, 
                a.description, 
                a.priority, 
                a.status, 
                a.total_hours, 
                a.start_date, 
                a.completion_date
""", nativeQuery = true)
    Page<ActivityWithManagerProjection> findAllActivitiesWithManager(Pageable pageable);
}