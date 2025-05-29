package com.sid.portal_web.service.activity;

import com.sid.portal_web.dto.response.ActivityResponse;

import java.util.List;

public interface IActivityService {


    // Método para obtener todas las actividades con manager
    List<ActivityResponse> getAllActivities();

    // Método para obtener una actividad específica
    ActivityResponse getActivityById(Integer activityId);



}
