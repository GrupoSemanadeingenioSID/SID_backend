package com.sid.portal_web.utils.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.sid.portal_web.core.team.Team;
import com.sid.portal_web.core.team.TeamMember;
import com.sid.portal_web.core.team.TeamProxy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class TeamCache {

    private final Cache<Integer, TeamProxy> teamCache = Caffeine.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    public void cacheSearchList(List<Team> teams) {
        teams.forEach(team -> {
            if (team instanceof TeamProxy proxy) {
                teamCache.put(proxy.getId(), proxy);
            }
        });
    }

    public Team getTeam(int id, Supplier<List<TeamMember>> membersSupplier) {
        TeamProxy team = teamCache.getIfPresent(id);
        if (team != null) {
            if (!team.isMembersLoaded()) {
                team.setTeam(membersSupplier);
               // team.getMembers();
            }
            teamCache.asMap().replace(team.getId(), team); // reemplazamos
            return team;
        }

        return null;
    }
}
