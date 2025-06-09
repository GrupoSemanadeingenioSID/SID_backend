package com.sid.portal_web.service.activity;


import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.mapper.ActivityMapper;
import com.sid.portal_web.repository.activity.ActivityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements IActivityService {

    private final ActivityRepository activityRepository;


    @Transactional
    public Page<ActivityResponse> getAllActivities(Pageable pageable) {

        return activityRepository.findAllActivitiesWithManager(pageable)
                .map(ActivityMapper::mapToResponse);
    }

    @Transactional
    public ActivityResponse getActivityById(Integer activityId) {
        return null;
    }


}
