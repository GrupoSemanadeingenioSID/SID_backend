package com.sid.portal_web.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.portal_web.core.Activities;
import com.sid.portal_web.dto.response.ActivityByIdResponse;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.dto.response.MemberDTO;
import com.sid.portal_web.entity.activity.ActivityEntity;
import com.sid.portal_web.entity.activity.projection.ActivityByIdProjection;
import com.sid.portal_web.entity.activity.projection.ActivityWithManagerProjection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ActivityMapper {


    public Activities entityToCore(ActivityEntity entity, String managerName) {
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

    public static ActivityResponse mapToResponse(ActivityWithManagerProjection projection) {


        return ActivityResponse.builder()
                .activityId(projection.getActivityId())
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

    public static ActivityByIdResponse projectionToResponse(ActivityByIdProjection projection){

        @Autowired
        private ObjectMapper objectMapper = null;


        //Falta solucionar
        return ActivityByIdResponse.builder()
                .activityId(projection.getActivityId())
                .title(projection.getTitle())
                .description(projection.getDescription())
                .priority(projection.getPriority())
                .status(projection.getStatus())
                .totalHours(projection.getTotalHours())
                .startDate(projection.getStartDate())
                .completionDate(projection.getCompletionDate())
                .manager(projection.getManagerName())
                .members(objectMapper.readValue(
                        projection.getMembers(), new TypeReference<List<MemberDTO>>() {}))
                .committees(objectMapper.readValue(
                        projection.getCommittees()))
                .build();
        }
    }
}
