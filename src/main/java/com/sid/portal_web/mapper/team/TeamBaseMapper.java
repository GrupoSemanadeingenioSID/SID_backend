package com.sid.portal_web.mapper.team;


import com.sid.portal_web.core.TeamBase;
import com.sid.portal_web.dto.response.TeamBaseResponse;
import org.springframework.stereotype.Component;

@Component
public class TeamBaseMapper {


    public TeamBase objectToDomain(Object[] object) {
        return new TeamBase(
                (Integer) object[0],
                (String) object[1],
                (String) object[2],
                (Boolean) object[3],
                (String) object[4]
        );
    }

    public TeamBaseResponse domainToResponse(TeamBase teamBase) {
        return TeamBaseResponse.builder()
                .id(teamBase.id())
                .name(teamBase.name())
                .description(teamBase.description())
                .active(teamBase.active())
                .leader(teamBase.leader())
                .build();
    }


}
