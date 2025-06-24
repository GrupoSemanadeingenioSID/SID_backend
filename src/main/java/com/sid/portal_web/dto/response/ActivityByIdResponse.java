package com.sid.portal_web.dto.response;

import com.sid.portal_web.entity.activity.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ActivityByIdResponse {

    private Integer activityId;
    private String title;
    private String description;
    private ActivityEntity.Priority priority;
    private ActivityEntity.Status status;
    private Integer totalHours;
    private LocalDate startDate;
    private LocalDate completionDate;
    private String manager;

    private List<MembersResponse> members;
    private List<CommitteesResponse> committees;
}
