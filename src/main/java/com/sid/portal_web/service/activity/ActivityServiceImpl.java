package com.sid.portal_web.service.activity;


import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.entity.activity.projection.ActivityWithManagerProjection;
import com.sid.portal_web.repository.activity.ActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements IActivityService {

    private final ActivityRepository activityRepository;


    @Transactional
    public Page<ActivityResponse> getAllActivities(Pageable pageable) {

        return activityRepository.findAllActivitiesWithManager(pageable)
                .map(this::mapToResponse);
    }

    @Transactional
    public ActivityResponse getActivityById(Integer activityId) {
        return null;
    }

    private ActivityResponse mapToResponse(ActivityWithManagerProjection projection) {


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
}
