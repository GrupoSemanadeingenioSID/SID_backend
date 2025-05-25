package com.sid.portal_web.repository.team;

import com.sid.portal_web.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepositoryJPA extends JpaRepository<TeamEntity, Integer> {
}
