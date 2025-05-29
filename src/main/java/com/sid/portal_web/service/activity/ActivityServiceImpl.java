package com.sid.portal_web.service.activity;


import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.entity.activity.projection.ActivityWithManagerProjection;
import com.sid.portal_web.repository.activity.ActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements IActivityService {

    private final ActivityRepository activityRepository;


    @Transactional
    public List<ActivityResponse> getAllActivities() {


        return activityRepository.findAllActivitiesWithManager()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ActivityResponse getActivityById(Integer activityId) {
        ActivityWithManagerProjection projection = activityRepository
                .findAllActivitiesWithManager()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        return mapToResponse(projection);
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
