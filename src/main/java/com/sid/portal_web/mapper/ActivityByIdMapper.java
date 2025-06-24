package com.sid.portal_web.mapper;

import com.sid.portal_web.core.Activities;
import com.sid.portal_web.dto.response.ActivityByIdResponse;
import com.sid.portal_web.dto.response.CommitteesResponse;
import com.sid.portal_web.dto.response.MembersResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityByIdMapper {





    public static ActivityByIdResponse coreToResponse(Activities activity, List<MembersResponse> members, List<CommitteesResponse> committees) {
        return ActivityByIdResponse
                .builder()
                .activityId(activity.activityId())
                .title(activity.title())
                .description(activity.description())
                .priority(activity.priority())
                .status(activity.status())
                .totalHours(activity.totalHours())
                .startDate(activity.startDate())
                .completionDate(activity.completionDate())
                .manager(activity.manager())
                .members(members)
                .committees(committees)
                .build();
    }

}
