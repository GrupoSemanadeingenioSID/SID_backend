package com.sid.portal_web.service.activity;


import com.sid.portal_web.core.Activities;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.entity.activity.ActivityEntity;
import com.sid.portal_web.mapper.ActivityMapper;
import com.sid.portal_web.repository.activity.ActivityRepository;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements IActivityService {

    private final ActivityRepository activityRepository;

    @Override
    public List<ActivityResponse> findAll() {

        List<ActivityEntity> activities = activityRepository.findAll();

        List<Activities>  activitiesCore = activities.stream()
                .map(ActivityMapper::entityToCore)
                .toList();

        return activitiesCore.stream()
                .map(ActivityMapper::coreToResponse)
                .collect(Collectors.toList());
    }
}
