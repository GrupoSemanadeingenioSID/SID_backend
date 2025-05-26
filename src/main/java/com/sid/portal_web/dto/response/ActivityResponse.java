package com.sid.portal_web.dto.response;

import com.sid.portal_web.entity.activity.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ActivityResponse {

    private Integer id;
    private String title;
    private String description;
    private ActivityEntity.Priority priority;
    private ActivityEntity.Status status;
    private Integer total_hours;
    private LocalDate startDate;
    private LocalDate completionDate;
    private String manager;


}
