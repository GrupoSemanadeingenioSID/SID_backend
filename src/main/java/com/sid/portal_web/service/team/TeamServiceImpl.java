package com.sid.portal_web.service.team;

import com.sid.portal_web.core.team.Team;
import com.sid.portal_web.dto.response.TeamResponse;
import com.sid.portal_web.mapper.team.TeamBaseMapper;
import com.sid.portal_web.repository.team.TeamRepository;
import com.sid.portal_web.utils.cache.TeamCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;
    private final TeamBaseMapper mapper;
    private final TeamCache teamCache; // <-- inyectás el cache

    @Override
    public Page<TeamResponse> findAll(Pageable page) {
        Page<Team> teams = repository.findAllProxy(page);
        // Aqui almacenamos en cache
        teamCache.cacheSearchList(teams.getContent());

        // retornamos la paginacion usando el mapper de una vez
        return new PageImpl<>(teams.stream()
                .map(mapper::domainToResponse)
                .toList(), page, teams.getTotalElements());
    }

    @Override
    public TeamResponse findById(Integer id) {
        // Cuando buscamos por id vamos a realizar la busqueda
        Team team = teamCache.getTeam(id, () -> repository.findMembersByTeamId(id));
        if (team == null) return mapper.domainToResponse(repository.findByTeamId(id));
        return mapper.domainToResponse(team);
    }
}
