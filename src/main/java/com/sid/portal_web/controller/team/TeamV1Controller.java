package com.sid.portal_web.controller.team;


import com.sid.portal_web.dto.request.TeamRequest;
import com.sid.portal_web.dto.response.TeamResponse;
import com.sid.portal_web.service.team.TeamService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamV1Controller {

    private final TeamService service;

    @GetMapping
    public ResponseEntity<Page<TeamResponse>> getTeams(
            @PageableDefault(sort = "formation_date", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable int id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id));
    }

    // Ahora vamos a realizar el apartado de post :
    @PostMapping
    public ResponseEntity<?> postTeam(@Valid @RequestBody TeamRequest teamRequest) {
        boolean success = service.saveTeamWithMembers(teamRequest);
        if (success) {
            ApiResponse response = ApiResponse.builder()
                    .code(201)
                    .message("Team created successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .code(500)
                            .message("Failed to create team")
                            .build());
        }
    }




    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class ApiResponse {
        private int code;
        private String message;
    }

}
