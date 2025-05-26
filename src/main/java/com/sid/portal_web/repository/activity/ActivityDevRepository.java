package com.sid.portal_web.repository.activity;

import com.sid.portal_web.entity.activity.ActivityDevEntity;
import com.sid.portal_web.entity.activity.ActivityDevId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDevRepository extends JpaRepository<ActivityDevEntity, ActivityDevId> {
}
