package com.sid.portal_web.repository.teamMembers;

import com.sid.portal_web.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMembersRepositoryJpa extends JpaRepository<TeamMemberEntity, Integer> {
}
