package com.sid.portal_web.repository.team;

import com.sid.portal_web.core.TeamBase;
import com.sid.portal_web.mapper.team.TeamBaseMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamBaseRepositoryImpl implements TeamBaseRepository {

    private final EntityManager manager;
    private final TeamBaseMapper mapper;

    @Override
    public Page<TeamBase> findAll(Pageable pageable) {

        String nativeQuery = """
            SELECT
                t.team_id AS id,
                t.name AS name,
                t.description AS description,
                t.active AS active,
                pf.name AS leader
            FROM team t
            INNER JOIN team_members tm ON t.team_id = tm.team_id
            INNER JOIN team_title tt ON tt.team_title_id = tm.team_title_id
            INNER JOIN development_member dm ON dm.development_member_id = tm.development_member_id
            INNER JOIN members mb ON mb.member_id = dm.member_id
            INNER JOIN users us ON us.user_id = mb.user_id
            INNER JOIN profile pf ON pf.user_id = us.user_id
            WHERE LOWER(tt.type_id) = 'lider'
            """;

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = manager.createNativeQuery(nativeQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<TeamBase> content = resultList.stream()
                .map(mapper::objectToDomain)
                .toList();

        // Consulta separada para el total de elementos
        String countQuery = """
            SELECT COUNT(DISTINCT t.team_id)
            FROM team t
            INNER JOIN team_members tm ON t.team_id = tm.team_id
            INNER JOIN team_title tt ON tt.team_title_id = tm.team_title_id
            WHERE LOWER(tt.type_id) = 'lider'
            """;

        long total = ((Number) manager.createNativeQuery(countQuery).getSingleResult()).longValue();

        return new PageImpl<>(content, pageable, total);
    }

}
