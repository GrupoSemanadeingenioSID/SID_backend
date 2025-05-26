package com.sid.portal_web.service.activity;

import com.sid.portal_web.dto.response.ActivityResponse;

import java.util.List;

public interface IActivityService {

    List<ActivityResponse> findAll();

}
