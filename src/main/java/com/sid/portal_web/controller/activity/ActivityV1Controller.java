package com.sid.portal_web.controller.activity;


import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.service.activity.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {

        return ResponseEntity.ok(activityService.getAllActivities());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable Integer id) {
        return ResponseEntity.ok(activityService.getActivityById(id));
    }

}
