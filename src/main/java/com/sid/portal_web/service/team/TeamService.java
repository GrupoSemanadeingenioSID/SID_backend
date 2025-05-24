package com.sid.portal_web.service.team;

import com.sid.portal_web.dto.response.TeamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {

    Page<TeamResponse> findAll(Pageable page);

    TeamResponse findById(Integer id);

}
