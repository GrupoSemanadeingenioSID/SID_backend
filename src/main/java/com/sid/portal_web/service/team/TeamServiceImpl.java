package com.sid.portal_web.service.team;

import com.sid.portal_web.dto.response.TeamBaseResponse;
import com.sid.portal_web.mapper.team.TeamBaseMapper;
import com.sid.portal_web.repository.team.TeamBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamBaseRepository repository;
    private final TeamBaseMapper mapper;

    @Override
    public Page<TeamBaseResponse> findAll(Pageable page) {
        var teams = repository.findAll(page);

        return new PageImpl<>(teams.stream().map(mapper::domainToResponse).toList(), page, teams.getTotalElements());
    }


}
