package com.sid.portal_web.service.team;

import com.sid.portal_web.dto.response.TeamBaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {

    Page<TeamBaseResponse> findAll(Pageable page);

}
