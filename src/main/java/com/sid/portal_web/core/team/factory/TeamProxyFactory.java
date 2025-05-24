package com.sid.portal_web.core.team.factory;


import com.sid.portal_web.core.team.Team;
import com.sid.portal_web.core.team.TeamProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamProxyFactory implements TeamFactory {

    @Override
    public Team createTeam(Object[] row) {
        return new TeamProxy(
                (Integer) row[0],
                (String) row[1],
                (String) row[2],
                (Boolean) row[3],
                (String) row[4]
        );
    }

}
