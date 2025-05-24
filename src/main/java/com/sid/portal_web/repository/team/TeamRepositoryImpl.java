package com.sid.portal_web.repository.team;

import com.sid.portal_web.core.team.Team;
import com.sid.portal_web.core.team.factory.TeamProxyFactory;
import com.sid.portal_web.core.team.TeamFull;
import com.sid.portal_web.core.team.TeamMember;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final EntityManager manager;
    private final TeamProxyFactory factory;

    @Override
    public Page<Team> findAllProxy(Pageable pageable) {

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

        List<Team> content = resultList.stream()
                .map(factory::createTeam)
                .toList();

        return new PageImpl<>(content, pageable, resultList.size());
    }


    @Override
    public Team findByTeamId(int id) {
        String teamQuery = """
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
                WHERE t.team_id = :teamId AND LOWER(tt.type_id) = 'lider'
                """;

        Object[] teamResult = (Object[]) manager.createNativeQuery(teamQuery)
                .setParameter("teamId", id)
                .getSingleResult();

        List<TeamMember> members = findMembersByTeamId(id);
        log.info("Loaded members for team {}", id);
        log.info("Members: {}",members);
        return new TeamFull(
                (Integer) teamResult[0],
                (String) teamResult[1],
                (String) teamResult[2],
                (Boolean) teamResult[3],
                (String) teamResult[4],
                members
        );
    }

    @Override
    public List<TeamMember> findMembersByTeamId(int teamId) {
        String membersQuery = """
                    SELECT pf.user_id as id,
                           pf.name    AS name,
                           rt.name    as rol,
                           tt.type_id AS title
                    FROM team_members tm
                             INNER JOIN team_title tt ON tt.team_title_id = tm.team_title_id
                             inner join rol_team rt on rt.rol_id = tm.rol_id
                             INNER JOIN development_member dm ON dm.development_member_id = tm.development_member_id
                             INNER JOIN members mb ON mb.member_id = dm.member_id
                             INNER JOIN users us ON us.user_id = mb.user_id
                             INNER JOIN profile pf ON pf.user_id = us.user_id
                    WHERE tm.team_id = :teamId
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> results = manager.createNativeQuery(membersQuery)
                .setParameter("teamId", teamId)
                .getResultList();
        log.info("Members: desde la query directa:  {}",results);
        return results.stream()
                .map(row -> new TeamMember(
                        (Integer) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .toList();
    }

}
