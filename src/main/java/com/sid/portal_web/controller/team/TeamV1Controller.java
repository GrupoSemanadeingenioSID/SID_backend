package com.sid.portal_web.controller.team;


import com.sid.portal_web.dto.response.TeamBaseResponse;
import com.sid.portal_web.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamV1Controller {

    private final TeamService service;

    @GetMapping
    public ResponseEntity<Page<TeamBaseResponse>> getTeams(
            @PageableDefault(sort = "formation_date", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(pageable));
    }


}
