package com.sid.portal_web.repository.team;


import com.sid.portal_web.core.team.Team;
import com.sid.portal_web.core.team.TeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamRepository {

    Page<Team> findAllProxy(Pageable pageable);
    Team findByTeamId(int id);
    List<TeamMember> findMembersByTeamId(int id);

}
