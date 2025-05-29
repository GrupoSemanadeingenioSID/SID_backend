package com.sid.portal_web.entity.activity.projection;

import com.sid.portal_web.entity.activity.ActivityEntity.Priority;
import com.sid.portal_web.entity.activity.ActivityEntity.Status;

import java.time.LocalDate;

public interface ActivityWithManagerProjection {
    Integer getActivityId();
    String getTitle();
    String getDescription();
    Priority getPriority();
    Status getStatus();
    Integer getTotalHours();
    LocalDate getStartDate();
    LocalDate getCompletionDate();
    String getManagerName();
}
