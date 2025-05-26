package com.sid.portal_web.controller.activity;


import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.service.activity.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityV1Controller {

    private IActivityService activityService;

    @Autowired
    public ActivityV1Controller(IActivityService activityService) {
        this.activityService = activityService;
    }


    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivities() {

        return ResponseEntity.ok(activityService.findAll());

    }

}
