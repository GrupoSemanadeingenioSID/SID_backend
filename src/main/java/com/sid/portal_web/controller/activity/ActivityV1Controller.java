package com.sid.portal_web.controller.activity;


import com.sid.portal_web.dto.response.ActivityByIdResponse;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.service.activity.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/activities")
public class ActivityV1Controller {

    private IActivityService activityService;

    @Autowired
    public ActivityV1Controller(IActivityService activityService) {
        this.activityService = activityService;
    }


    @GetMapping
    public ResponseEntity<Page<ActivityResponse>> getAll(Pageable pageable) {

        return ResponseEntity.ok(activityService.getAll(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityByIdResponse> getById(@PathVariable Integer id) {
        return activityService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
