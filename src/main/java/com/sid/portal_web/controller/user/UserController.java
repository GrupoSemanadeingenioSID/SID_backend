package com.sid.portal_web.controller.user;


import com.sid.portal_web.dto.response.UserWithProfileResponse;
import com.sid.portal_web.service.userWithProfile.UserWithProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserWithProfileService service;

    @GetMapping
    public List<UserWithProfileResponse> getUsers() {
        return service.findAll();
    }


}
