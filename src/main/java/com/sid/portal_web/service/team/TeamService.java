package com.sid.portal_web.service.team;

import com.sid.portal_web.dto.request.TeamRequest;
import com.sid.portal_web.dto.response.TeamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {

    Page<TeamResponse> findAll(Pageable page);

    TeamResponse findById(Integer id);

    // Aaqui tengo que guardar donde va cada uno:
    // Qué tipo de datos y en que tablas debo guardar? :

    boolean saveTeamWithMembers(TeamRequest request);

    boolean updateTeam(Integer id, TeamRequest request);

}
