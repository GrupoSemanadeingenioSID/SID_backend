package com.sid.portal_web.core.team;

import java.util.List;

public interface Team {
    Integer getId();
    String getName();
    String getDescription();
    Boolean getActive();
    String getLeader();
    List<TeamMember> getMembers();
}