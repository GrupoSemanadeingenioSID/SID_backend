package com.sid.portal_web.service.team;

import com.sid.portal_web.core.team.Team;
import com.sid.portal_web.core.team.TeamProxy;
import com.sid.portal_web.dto.request.TeamRequest;
import com.sid.portal_web.dto.response.TeamResponse;
import com.sid.portal_web.entity.TeamEntity;
import com.sid.portal_web.entity.TeamMemberEntity;
import com.sid.portal_web.error.TeamException;
import com.sid.portal_web.mapper.team.TeamBaseMapper;
import com.sid.portal_web.repository.team.TeamRepository;
import com.sid.portal_web.repository.team.TeamRepositoryJPA;
import com.sid.portal_web.repository.teamMembers.TeamMembersRepositoryJpa;
import com.sid.portal_web.utils.cache.TeamCache;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamRepositoryJPA teamRepositoryJPA;
    private final TeamMembersRepositoryJpa teamMembersRepositoryJpa;
    private final TeamBaseMapper mapper;
    private final TeamCache teamCache;

    @Override
    @Transactional(readOnly = true)
    public Page<TeamResponse> findAll(Pageable page) {
        Page<Team> teams = teamRepository.findAllProxy(page);

        // Cachear la lista obtenida
        teamCache.cacheSearchList(teams.getContent());

        return new PageImpl<>(
                teams.getContent().stream()
                        .map(mapper::domainToResponse)
                        .toList(),
                page,
                teams.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public TeamResponse findById(Integer id) {
        // Intentar obtener del cache primero
        Team team = teamCache.getTeam(id, () -> teamRepository.findMembersByTeamId(id));

        // Si no está en cache, buscar en BD
        if (team == null) {
            team = teamRepository.findByTeamId(id);

            // Si encontramos el team y es un proxy, cachearlo
            if (team instanceof TeamProxy proxy) {
                proxy.setTeam(() -> teamRepository.findMembersByTeamId(id));
                teamCache.cacheTeam(proxy);
            }
        }

        if (team == null) {
            throw TeamException.teamNotFound(id);
        }

        return mapper.domainToResponse(team);
    }

    @Override
    public boolean saveTeamWithMembers(TeamRequest request) {
        try {
            // Crear y guardar el team
            TeamEntity teamEntity = TeamEntity.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .active(true)
                    .formationDate(request.getFormation_date())
                    .projectId(request.getProjectId())
                    .build();

            TeamEntity savedTeam = teamRepositoryJPA.save(teamEntity);

            // Crear y guardar los miembros
            if (request.getMembers() != null && !request.getMembers().isEmpty()) {
                List<TeamMemberEntity> teamMembers = request.getMembers().stream()
                        .map(member -> TeamMemberEntity.builder()
                                .joindDate(member.getJoin_date())
                                .endDate(member.getEnd_date())
                                .rolId(member.getRol_id())
                                .teamId(savedTeam.getTeamId())
                                .developmentMemberId(member.getDevelopment_member_id())
                                .teamTitleId(member.getTitle_id())
                                .build())
                        .toList();

                teamMembersRepositoryJpa.saveAll(teamMembers);
            }

            return true;

        } catch (Exception e) {
            log.error("Error saving team with members: {}", e.getMessage(), e);
            throw TeamException.errorCreatingTeam();
        }
    }

    @Override
    public boolean updateTeam(Integer id, TeamRequest request) {
        TeamEntity existingTeam = teamRepositoryJPA.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        // Actualizar campos
        existingTeam.setName(request.getName());
        existingTeam.setDescription(request.getDescription());
        existingTeam.setFormationDate(request.getFormation_date());

        teamRepositoryJPA.save(existingTeam);

        // Invalidar cache después de actualizar
        teamCache.invalidateTeam(id);

        return true;
    }
}