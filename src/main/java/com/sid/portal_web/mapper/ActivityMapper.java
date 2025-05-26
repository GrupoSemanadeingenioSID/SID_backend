package com.sid.portal_web.mapper;

import com.sid.portal_web.core.Activities;
import com.sid.portal_web.entity.activity.ActivityEntity;

public interface ActivityMapper {

    static Activities entityToCore(ActivityEntity entity){
        return new Activities(
                entity.getTitle(),
                entity.getDescription(),
                entity.getPriority(),
                entity.getStatus(),
                entity.getTotalHours(),
                entity.getStartDate(),
                entity.getCompletionDate(),
                entity.getParticipationDev() != null ?
                        entity.getParticipationDev().
        )
    }

}
