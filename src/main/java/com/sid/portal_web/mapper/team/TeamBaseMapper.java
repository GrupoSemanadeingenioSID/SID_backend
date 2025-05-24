package com.sid.portal_web.mapper.team;


import com.sid.portal_web.core.team.Team;
import com.sid.portal_web.dto.response.TeamResponse;
import org.springframework.stereotype.Component;

@Component
public class TeamBaseMapper {

    public TeamResponse domainToResponse(Team teamProxy) {
        return TeamResponse.builder()
                .id(teamProxy.getId())
                .name(teamProxy.getName())
                .description(teamProxy.getDescription())
                .active(teamProxy.getActive())
                .leader(teamProxy.getLeader())
                .members(teamProxy.getMembers())
                .build();
    }


}
