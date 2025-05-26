package com.sid.portal_web.mapper;

import com.sid.portal_web.core.Activities;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.entity.activity.ActivityEntity;
import com.sid.portal_web.entity.activity.ActivityParticipationEntity;

import java.util.Set;

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
                extractPrimaryManagerName(entity.getActivityParticipation())
        );
    }

    static ActivityResponse coreToResponse(Activities core){
        return ActivityResponse.builder()
                .title(core.title())
                .description(core.description())
                .priority(core.priority())
                .status(core.status())
                .totalHours(core.totalHours())
                .startDate(core.startDate())
                .completionDate(core.completionDate())
                .manager(core.manager())
                .build();
    }


    // Helper para extraer el manager principal
    private static String extractPrimaryManagerName(Set<ActivityParticipationEntity> participations) {
        return participations.stream()
                .filter(p -> "Lider".equals(p.getTitle().getDescription()))
                .findFirst()
                .map(p -> p.getTitle().getDescription())
                .orElse(null);
    }

}
