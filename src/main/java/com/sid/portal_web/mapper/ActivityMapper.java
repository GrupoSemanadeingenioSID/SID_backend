package com.sid.portal_web.mapper;

import com.sid.portal_web.core.Activities;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.entity.activity.ActivityEntity;
import com.sid.portal_web.entity.activity.ActivityParticipationEntity;
import com.sid.portal_web.entity.activity.projection.ActivityWithManagerProjection;

import java.util.Set;

public class ActivityMapper {

    // Método para mapear desde la proyección
    public static ActivityResponse projectionToResponse(ActivityWithManagerProjection projection) {
        return ActivityResponse.builder()
                .title(projection.getTitle())
                .description(projection.getDescription())
                .priority(projection.getPriority())
                .status(projection.getStatus())
                .totalHours(projection.getTotalHours())
                .startDate(projection.getStartDate())
                .completionDate(projection.getCompletionDate())
                .manager(projection.getManagerName())
                .build();
    }

    // Método para mapear desde la entidad + manager
    public static ActivityResponse entityToResponse(ActivityEntity entity, String managerName) {
        return ActivityResponse.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .priority(entity.getPriority())
                .status(entity.getStatus())
                .totalHours(entity.getTotalHours())
                .startDate(entity.getStartDate())
                .completionDate(entity.getCompletionDate())
                .manager(managerName)
                .build();
    }
}
