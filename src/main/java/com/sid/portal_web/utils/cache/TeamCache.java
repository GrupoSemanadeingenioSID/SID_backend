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
                // Solo cachear si no existe ya
                teamCache.asMap().putIfAbsent(proxy.getId(), proxy);
            }
        });
    }

    public Team getTeam(int id, Supplier<List<TeamMember>> membersSupplier) {
        TeamProxy cachedTeam = teamCache.getIfPresent(id);

        if (cachedTeam != null) {
            // Si está en cache, configurar el supplier para lazy loading
            if (!cachedTeam.isMembersLoaded()) {
                cachedTeam.setTeam(membersSupplier);
            }
            return cachedTeam;
        }

        // No está en cache, devolver null para que el servicio maneje el fallback
        return null;
    }

    // caché indivudal
    public void cacheTeam(TeamProxy team) {
        if (team != null) {
            teamCache.put(team.getId(), team);
        }
    }
    public void invalidateTeam(Integer id) {
        teamCache.invalidate(id);
    }

    //estadisticas para debuggin
    public String getCacheStats() {
        return teamCache.stats().toString();
    }
}