package com.sid.portal_web.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.portal_web.core.Activities;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.entity.activity.ActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ActivityMapper {

    @Autowired
    private ObjectMapper objectMapper;

    public static Activities entityToCore(ActivityEntity entity, String managerName) {
        return new Activities(
                entity.getActivityId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPriority(),
                entity.getStatus(),
                entity.getTotalHours(),
                entity.getStartDate(),
                entity.getCompletionDate(),
                managerName);
    }

    public static ActivityResponse coreToResponse(Activities core) {
        return ActivityResponse.builder()
                .activityId(core.activityId())
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

}
