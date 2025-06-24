package com.sid.portal_web.service.activity;

import com.sid.portal_web.dto.response.ActivityByIdResponse;
import com.sid.portal_web.dto.response.ActivityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface IActivityService {

    //Obtener todas las actividades
    Page<ActivityResponse> getAll(Pageable pageable);

    //Obtener por id
    Optional<ActivityByIdResponse> getById(Integer activityId);



}
