package com.sid.portal_web.repository.activity;

import com.sid.portal_web.entity.activity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {

}