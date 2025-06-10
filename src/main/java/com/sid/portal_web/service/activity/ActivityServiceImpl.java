package com.sid.portal_web.service.activity;


import com.sid.portal_web.dto.response.ActivityByIdResponse;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.entity.activity.projection.ActivityByIdProjection;
import com.sid.portal_web.mapper.ActivityMapper;
import com.sid.portal_web.repository.activity.ActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements IActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;


    @Transactional
    public Page<ActivityResponse> getAllActivities(Pageable pageable) {

        return activityRepository.findAllActivitiesWithManager(pageable)
                .map(ActivityMapper::mapToResponse);
    }

    @Transactional
    public ActivityByIdResponse getActivityById(Integer activityId) {
        ActivityByIdProjection projection = activityRepository
                .findActivityById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        return activityMapper.projectionToResponse(projection);
    }
}
