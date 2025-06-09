package com.sid.portal_web.service.activity;

import com.sid.portal_web.dto.response.ActivityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IActivityService {


    // Método para obtener todas las actividades con manager
    Page<ActivityResponse> getAllActivities(Pageable pageable);

    // Método para obtener una actividad específica
    ActivityResponse getActivityById(Integer activityId);



}
