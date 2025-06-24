package com.sid.portal_web.repository.activity;

import com.sid.portal_web.entity.activity.ActivityParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipationEntity,Integer> {
    @Query("SELECT a.activityId, am.name FROM ActivityParticipationEntity ap " +
            "LEFT JOIN ap.activity a " +
            "LEFT JOIN ap.activityMember am " +
            "LEFT JOIN ap.title at " +
            "WHERE LOWER(at.description) = 'líder'")
    List<Object[]> findByLeaderTitle();

    @Query("SELECT am.name FROM ActivityParticipationEntity ap " +
            "LEFT JOIN ap.activity a " +
            "LEFT JOIN ap.activityMember am " +
            "LEFT JOIN ap.title at " +
            "WHERE LOWER(at.description) = 'líder' " +
            "AND a.activityId = :activityId")
    String findByLeaderTitleById(@Param("activityId") Integer activityId);

}
